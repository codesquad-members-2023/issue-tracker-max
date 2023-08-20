import { useState } from "react";
import { styled } from "styled-components";
import { TextInput, TextInputProps } from "./TextInput";
import { DropdownContainer } from "./dropdown/DropdownContainer";

type FilterBarProps = {
  name: string;
  optionTitle: string;
  options: {
    id: number;
    name: string;
    profile?: string;
    selected: boolean;
    onClick: () => void;
  }[];
} & TextInputProps;

export function FilterBar({
  name,
  optionTitle,
  options,
  ...rest
}: FilterBarProps) {
  const [state, setState] = useState<"Enabled" | "Active">("Enabled");

  const handleFilterBarFocus = () => {
    setState("Active");
  };

  const handleFilterBarBlur = () => {
    setState("Enabled");
  };

  return (
    <Div
      onFocus={handleFilterBarFocus}
      onBlur={handleFilterBarBlur}
      $state={state}
    >
      <DropdownContainer
        name={name}
        optionTitle={optionTitle}
        options={options}
        alignment="Left"
        autoClose
      />
      <TextInput icon="Search" {...rest} />
    </Div>
  );
}

const Div = styled.div<{ $state: "Enabled" | "Active" }>`
  display: flex;
  align-items: center;
  gap: 1px;
  width: 560px;
  border: ${({ theme, $state }) =>
    $state === "Active"
      ? `${theme.border.default} ${theme.color.neutralBorderDefaultActive}`
      : `${theme.border.default} ${theme.color.neutralBorderDefault}`};
  border-radius: ${({ theme }) => theme.radius.medium};
  background-color: ${({ theme }) => theme.color.neutralBorderDefault};

  & > div:first-child {
    border-radius: ${({ theme }) =>
      `${theme.radius.medium} 0 0 ${theme.radius.medium}`};
    background-color: ${({ theme, $state }) =>
      $state === "Enabled"
        ? theme.color.neutralSurfaceDefault
        : theme.color.neutralSurfaceStrong};

    & > button {
      height: 40px;
      padding: 0 24px;
    }
  }

  & > div:last-child {
    flex: 1;
  }

  & > div:last-child > div {
    width: 100%;
    border: none;
    border-radius: ${({ theme }) =>
      `0 ${theme.radius.medium} ${theme.radius.medium} 0`};
    background-color: ${({ theme, $state }) =>
      $state === "Enabled"
        ? theme.color.neutralSurfaceBold
        : theme.color.neutralSurfaceStrong};

    & > input {
      flex: 1;
    }
  }
`;
