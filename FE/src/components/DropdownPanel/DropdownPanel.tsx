import { styled } from "styled-components";
import { useEffect, useRef, useState } from "react";
import DropdownItem from "./DropdownItem";
import { AssigneesList, DropdownMilestone, Label } from "../../type";
import { useNavigate } from "react-router-dom";

type Props = {
  type?: "filter" | "assignees" | "labels" | "milestones" | "authors";
  top: string;
  left: string;
  closeDropdown(): void;
};

type AssigneesData = {
  assignees: AssigneesList[] | null;
};

type LabelsData = {
  labels: Label[] | null;
};

type MilestonesData = {
  milestones: DropdownMilestone[] | null;
};

type AuthorsData = {
  authors: AssigneesList[] | null;
};

export default function DropdownPanel({
  type = "filter",
  top,
  left,
  closeDropdown,
}: Props) {
  const navigate = useNavigate();
  const dropdownRef = useRef<HTMLDivElement>(null);
  const [assigneesData, setAssigneesData] = useState<AssigneesData | null>(
    null,
  );
  const [labelsData, setLabelsData] = useState<LabelsData | null>(null);
  const [milestonesData, setMilestonesData] = useState<MilestonesData | null>(
    null,
  );
  const [authorsData, setAuthorsData] = useState<AuthorsData | null>(null);

  const showOpenIssue = () => {
    closeDropdown();
    navigate("/issues/isOpen=true");
  };

  const showCloseIssue = () => {
    closeDropdown();
    navigate("/issues/isOpen=false");
  };

  const showAuthoredByMe = () => {
    closeDropdown();
    navigate("/issues/authorId=1");
  };

  const showAssignedIssue = () => {
    closeDropdown();
    navigate("/issues/assigneeId=1");
  };

  const showCommentedIssue = () => {
    closeDropdown();
    navigate("/issues/isCommentedByMe=true");
  };

  useEffect(() => {
    if (type === "filter") {
      return;
    }
    const URL = `http://3.34.141.196/api/issues/${type}`;
    const fetchData = async () => {
      try {
        const response = await fetch(URL);
        if (!response.ok) {
          throw new Error("데이터를 가져오는 데 실패했습니다.");
        }
        const jsonData = await response.json();
        type === "assignees"
          ? setAssigneesData(jsonData)
          : type === "labels"
          ? setLabelsData(jsonData)
          : type === "milestones"
          ? setMilestonesData(jsonData)
          : setAuthorsData(jsonData);
      } catch (error) {
        console.log("error");
      }
    };

    fetchData();
  }, []);

  useEffect(() => {
    const handleOutsideClick = (e: MouseEvent) => {
      if (
        dropdownRef.current &&
        !dropdownRef.current.contains(e.target as Node)
      ) {
        closeDropdown();
      }
    };

    document.addEventListener("mousedown", handleOutsideClick);

    return () => {
      document.removeEventListener("mousedown", handleOutsideClick);
    };
  }, [closeDropdown]);

  return (
    <Container ref={dropdownRef} $top={top} $left={left}>
      <Header>
        <Title>
          {type === "filter"
            ? "이슈 필터"
            : type === "assignees"
            ? "담당자 필터"
            : type === "labels"
            ? "레이블 필터"
            : type === "milestones"
            ? "마일스톤 필터"
            : "작성자 필터"}
        </Title>
      </Header>
      {type === "filter" && (
        <div>
          <DropdownItem itemName={"열린 이슈"} onClick={showOpenIssue} />
          <DropdownItem
            itemName={"내가 작성한 이슈"}
            onClick={showAuthoredByMe}
          />
          <DropdownItem
            itemName={"나에게 할당된 이슈"}
            onClick={showAssignedIssue}
          />
          <DropdownItem
            itemName={"내가 댓글을 남긴 이슈"}
            onClick={showCommentedIssue}
          />
          <DropdownItem itemName={"닫힌 이슈"} onClick={showCloseIssue} />
        </div>
      )}
      {type === "assignees" &&
        assigneesData &&
        assigneesData.assignees!.map((assignee) => (
          <DropdownItem
            key={assignee.id}
            userImg={assignee.profileImageUrl}
            itemName={assignee.nickname}
            onClick={() => {
              closeDropdown();
              navigate(`/issues/assigneeId=${assignee.id}`);
            }}
          />
        ))}
      {type === "labels" &&
        labelsData &&
        labelsData.labels!.map((label) => (
          <DropdownItem
            key={label.id}
            itemName={label.title}
            onClick={() => {
              closeDropdown();
              navigate(`/issues/labelIds=${label.id}`);
            }}
          />
        ))}
      {type === "milestones" &&
        milestonesData &&
        milestonesData.milestones!.map((milestone) => (
          <DropdownItem
            key={milestone.id}
            itemName={milestone.title}
            onClick={() => {
              closeDropdown();
              navigate(`/issues/milestoneId=${milestone.id}`);
            }}
          />
        ))}
      {type === "authors" &&
        authorsData &&
        authorsData.authors!.map((author) => (
          <DropdownItem
            key={author.id}
            itemName={author.nickname}
            userImg={author.profileImageUrl}
            onClick={() => {
              closeDropdown();
              navigate(`/issues/authorId=${author.id}`);
            }}
          />
        ))}
    </Container>
  );
}

const Container = styled.div<{ $top: string; $left: string }>`
  position: absolute;
  z-index: 1000;
  top: ${({ $top }) => $top};
  left: ${({ $left }) => $left};
  width: 240px;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.strong};
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.large};
  > div:last-child {
    border-bottom-left-radius: inherit;
    border-bottom-right-radius: inherit;
  }
`;

const Header = styled.div`
  padding: 8px 16px;
  border-top-left-radius: inherit;
  border-top-right-radius: inherit;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.default};
`;

const Title = styled.span`
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;
