import { useCallback, useEffect, useState } from "react";
import { sameNumbers } from "../../utils/sameNumbers";
import { InformationTag } from "../InformationTag";
import { DropdownContainer } from "../dropdown/DropdownContainer";
import { IconColor } from "../icon/Icon";
import { ElementContainer } from "./ElementContainer";
import { OptionDiv } from "./Sidebar";

export type IssueLabel = {
  id: number;
  name: string;
  color: "LIGHT" | "DARK";
  background: string;
};

export type LabelData = {
  id: number;
  name: string;
  color: "LIGHT" | "DARK";
  background: IconColor;
  selected: boolean;
  onClick: () => void;
};

export type AddLabelProps = {
  issueLabels: IssueLabel[];
  onLabelClick:
    | { args: "NumberArray"; handler: (ids: number[]) => void }
    | { args: "DataArray"; handler: (labels: IssueLabel[]) => void };
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
    const selectedLabels = labels.filter(({ selected }) => selected);
    const selectedIds = selectedLabels.map(({ id }) => id);
    if (
      sameNumbers(
        selectedIds,
        issueLabels.map(({ id }) => id),
      )
    ) {
      return;
    }

    if (onLabelClick.args === "NumberArray") {
      onLabelClick.handler(selectedIds);
    } else {
      onLabelClick.handler(
        selectedLabels.map(({ id, name, color, background }) => ({
          id,
          name,
          color,
          background,
        })),
      );
    }
  };

  return (
    <OptionDiv>
      <DropdownContainer
        key="labels"
        name="레이블 "
        optionTitle="레이블 설정"
        options={labels}
        type="Long"
        iconType="Palette"
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
