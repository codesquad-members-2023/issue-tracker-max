import { useCallback, useEffect, useState } from "react";
import { DropdownContainer } from "../dropdown/DropdownContainer";
import { AssigneeElement } from "./AssigneeElement";
import { ElementContainer } from "./ElementContainer";
import { OptionDiv } from "./Sidebar";
import { sameNumbers } from "../../utils/sameNumbers";

type AssigneeData = {
  id: number;
  name: string;
  profile: string;
  selected: boolean;
  onClick: () => void;
};

type AddAssigneeProps = {
  issueAssignees: {
    id: number;
    name: string;
    avatarUrl: string;
  }[];
  onAssigneeClick: (ids: number[]) => void;
};

export function AddAssignee({
  issueAssignees,
  onAssigneeClick,
}: AddAssigneeProps) {
  const [assignees, setAssignees] = useState<AssigneeData[]>([]);

  const fetchAssignees = useCallback(async () => {
    const response = await fetch("/api/assignees");
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
    const selectedIds = assignees
      .filter(({ selected }) => selected)
      .map(({ id }) => id);
    if (
      sameNumbers(
        selectedIds,
        issueAssignees.map(({ id }) => id),
      )
    ) {
      return;
    }
    onAssigneeClick(selectedIds);
  };

  return (
    <OptionDiv>
      <DropdownContainer
        key="assignees"
        name="담당자"
        optionTitle="담당자 설정"
        options={assignees}
        type="Long"
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
