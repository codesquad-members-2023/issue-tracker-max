import { styled } from "styled-components";

type Props = {
  id?: string;
  placeholder?: string;
  handleFocus?(): void;
  handleBlur?(): void;
  updateInputValue?(value: string): void;
};

export default function Input({
  id,
  placeholder,
  handleFocus,
  handleBlur,
  updateInputValue,
}: Props) {
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    updateInputValue!(e.target.value);
  };

  return (
    <TextInput
      id={id}
      type="text"
      placeholder={placeholder}
      onChange={handleInputChange}
      onFocus={handleFocus}
      onBlur={handleBlur}
    />
  );
}

const TextInput = styled.input`
  width: 100%;
  height: 24px;
  background-color: transparent;
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.default};
  caret-color: ${({ theme }) => theme.colorSystem.brand.surface.default};
  &::placeholder {
    color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
  }
  &:focus {
    outline: none;
    color: ${({ theme }) => theme.colorSystem.neutral.text.strong};
  }
`;
