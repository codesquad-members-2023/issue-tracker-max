import { useCallback, useEffect, useState } from "react";
import { getAccessToken } from "../../utils/localStorage";
import { sameNumbers } from "../../utils/sameNumbers";
import { DropdownContainer } from "../dropdown/DropdownContainer";
import { AssigneeElement } from "./AssigneeElement";
import { ElementContainer } from "./ElementContainer";
import { OptionDiv } from "./Sidebar";

export type IssueAssignee = {
  id: number;
  name: string;
  avatarUrl: string;
};

export type AssigneeData = {
  id: number;
  name: string;
  profile: string;
  selected: boolean;
  onClick: () => void;
};

export type AddAssigneeProps = {
  issueAssignees: IssueAssignee[];
  onAssigneeClick:
    | { args: "NumberArray"; handler: (ids: number[]) => void }
    | { args: "DataArray"; handler: (assignees: IssueAssignee[]) => void };
};

export function AddAssignee({
  issueAssignees,
  onAssigneeClick,
}: AddAssigneeProps) {
  const [assignees, setAssignees] = useState<AssigneeData[]>([]);

  const fetchAssignees = useCallback(async () => {
    const response = await fetch("/api/assignees", {
      method: "GET",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAccessToken()}`,
      },
    });
    const result = await response.json();

    const assigneesData = result.data.assignees.map(
      (assignee: { id: number; loginId: string; avatarUrl: string | null }) => {
        return {
          id: assignee.id,
          name: assignee.loginId,
          profile: assignee.avatarUrl,
          selected: false,
          onClick: () => {
            setAssignees((a) =>
              a.map((item) => ({
                ...item,
                selected:
                  item.id === assignee.id ? !item.selected : item.selected,
              })),
            );
          },
        };
      },
    );

    setAssignees(assigneesData);
  }, []);

  useEffect(() => {
    fetchAssignees();
  }, [fetchAssignees]);

  useEffect(() => {
    setAssignees((a) =>
      a.map((item) => ({
        ...item,
        selected: issueAssignees.some(({ id }) => id === item.id),
      })),
    );
  }, [issueAssignees]);

  const patchIssueAssignees = () => {
    const selectedAssignees = assignees.filter(({ selected }) => selected);
    const selectedIds = selectedAssignees.map(({ id }) => id);
    if (
      sameNumbers(
        selectedIds,
        issueAssignees.map(({ id }) => id),
      )
    ) {
      return;
    }

    if (onAssigneeClick.args === "NumberArray") {
      onAssigneeClick.handler(selectedIds);
    } else {
      onAssigneeClick.handler(
        selectedAssignees.map(({ id, name, profile }) => ({
          id,
          name,
          avatarUrl: profile,
        })),
      );
    }
  };

  return (
    <OptionDiv>
      <DropdownContainer
        key="assignees"
        name="담당자"
        optionTitle="담당자 설정"
        options={assignees}
        type="Long"
        iconType="Profile"
        alignment="Center"
        onDimClick={patchIssueAssignees}
      />
      {issueAssignees.length > 0 && (
        <ElementContainer direction="Vertical">
          {issueAssignees.map(({ id, name, avatarUrl }) => (
            <AssigneeElement key={id} name={name} profile={avatarUrl} />
          ))}
        </ElementContainer>
      )}
    </OptionDiv>
  );
}
