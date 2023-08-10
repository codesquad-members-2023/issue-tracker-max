import { styled } from "styled-components";

type Props = {
  id?: string;
  inputType?: "text" | "password";
  placeholder?: string;
  handleFocus?(): void;
  handleBlur?(): void;
  onChange?(e: React.ChangeEvent<HTMLInputElement>): void;
  value?: string;
  maxLength?: number;
  handleEnterFilter?(): void;
};

export default function Input({
  id,
  inputType = "text",
  placeholder,
  handleFocus,
  handleBlur,
  onChange,
  value,
  maxLength,
  handleEnterFilter,
}: Props) {
  const enterKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      e.preventDefault();
      handleEnterFilter && handleEnterFilter();
    }
  };

  return (
    <TextInput
      id={id}
      type={inputType}
      placeholder={placeholder}
      onChange={onChange}
      onFocus={handleFocus}
      onBlur={handleBlur}
      value={value}
      maxLength={maxLength}
      onKeyPress={enterKeyPress}
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
