import { styled } from "styled-components";

type Props = {
  id?: string;
  inputLabel: string;
  size?: "L" | "S";
  updateInputValue?(value: string): void;
};

export default function Input({
  id,
  inputLabel,
  size = "L",
  updateInputValue,
}: Props) {
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    updateInputValue!(e.target.value);
  };

  return (
    <TextInput
      id={id}
      inputLabel={inputLabel}
      type="text"
      placeholder={inputLabel}
      size={size}
      onChange={handleInputChange}
      updateInputValue={updateInputValue}
    />
  );
}

const TextInput = styled.input<Props>`
  width: ${({ size }) => (size === "L" ? "256px" : "184px")};
  height: 24px;
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.default};
  background-color: transparent;
  caret-color: ${({ theme }) => theme.colorSystem.brand.surface.default};
  &::placeholder {
    color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
  }
  &:focus {
    outline: none;
  }
`;
