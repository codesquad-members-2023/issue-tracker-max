import { useCallback, useEffect, useState } from "react";
import { InformationTag } from "../InformationTag";
import { DropdownContainer } from "../dropdown/DropdownContainer";
import { IconColor } from "../icon/Icon";
import { ElementContainer } from "./ElementContainer";
import { OptionDiv } from "./Sidebar";
import { sameNumbers } from "../../utils/sameNumbers";

type LabelData = {
  id: number;
  name: string;
  color: "LIGHT" | "DARK";
  background: IconColor;
  selected: boolean;
  onClick: () => void;
};

type AddLabelProps = {
  issueLabels: {
    id: number;
    name: string;
    color: "LIGHT" | "DARK";
    background: string;
  }[];
  onLabelClick: (ids: number[]) => void;
};

export function AddLabel({ issueLabels, onLabelClick }: AddLabelProps) {
  const [labels, setLabels] = useState<LabelData[]>([]);

  const fetchLabels = useCallback(async () => {
    const response = await fetch("/api/labels");
    const result = await response.json();

    const labelsData = result.data.labels.map(
      (label: {
        id: number;
        name: string;
        color: string;
        background: string;
      }) => {
        return {
          id: label.id,
          name: label.name,
          color: label.color,
          background: label.background,
          selected: false,
          onClick: () => {
            setLabels((l) =>
              l.map((item) => ({
                ...item,
                selected: item.id === label.id ? !item.selected : item.selected,
              })),
            );
          },
        };
      },
    );

    setLabels(labelsData);
  }, []);

  useEffect(() => {
    fetchLabels();
  }, [fetchLabels]);

  useEffect(() => {
    setLabels((l) =>
      l.map((item) => ({
        ...item,
        selected: issueLabels.some(({ id }) => id === item.id),
      })),
    );
  }, [issueLabels]);

  const patchIssueLabels = () => {
    const selectedIds = labels
      .filter(({ selected }) => selected)
      .map(({ id }) => id);
    if (
      sameNumbers(
        selectedIds,
        issueLabels.map(({ id }) => id),
      )
    ) {
      return;
    }
    onLabelClick(selectedIds);
  };

  return (
    <OptionDiv>
      <DropdownContainer
        key="labels"
        name="레이블 "
        optionTitle="레이블 설정"
        options={labels}
        type="Long"
        alignment="Center"
        onDimClick={patchIssueLabels}
      />
      {issueLabels.length > 0 && (
        <ElementContainer direction="Horizontal">
          {issueLabels.map(({ id, name, color, background }) => (
            <InformationTag
              key={id}
              value={name}
              size="S"
              fill={background as IconColor}
              fontColor={color}
            />
          ))}
        </ElementContainer>
      )}
    </OptionDiv>
  );
}
