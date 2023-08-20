import labelIcon from "@assets/icon/label.svg";
import milestoneIcon from "@assets/icon/milestone.svg";
import plusIcon from "@assets/icon/plus.svg";
import Button from "@components/common/Button";
import TabBar from "@components/common/TabBar";
import useFetch from "@hooks/useFetch";
import { getLabelCount, getMilestoneCount } from "api";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";

export default function NavigationBar() {
  const navigate = useNavigate();
  const { data: labelCount } = useFetch(getLabelCount);
  const { data: milestoneCount } = useFetch(getMilestoneCount);

  const moveToNewIssuePage = () => navigate("/issues/new");

  return (
    <StyledNavigationBar>
      <TabBar
        currentTabName=""
        left={{
          name: "레이블",
          count: labelCount?.count,
          iconSrc: labelIcon,
          callback: () => navigate("/labels"),
        }}
        right={{
          name: "마일스톤",
          count: milestoneCount?.count,
          iconSrc: milestoneIcon,
          callback: () => navigate("/milestones"),
        }}
        borderStyle="outline"
      />
      <Button size="S" variant="container" onClick={moveToNewIssuePage}>
        <img src={plusIcon} alt="이슈 작성" />
        이슈 작성
      </Button>
    </StyledNavigationBar>
  );
}

const StyledNavigationBar = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
`;
