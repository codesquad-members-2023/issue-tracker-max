import { styled } from "styled-components";
import Input from "../Input/Input";

type Props = {
  icon?: string;
  id: string;
  label: string;
  placeholder: string;
  value?: string;
  maxLength?: number;
  onChange?(e: React.ChangeEvent<HTMLInputElement>): void;
  onClick?(): void;
};

export default function LabelInput({
  icon,
  id,
  label,
  placeholder,
  value,
  maxLength,
  onChange,
  onClick,
}: Props) {
  return (
    <Container>
      <Label htmlFor={id}>{label}</Label>
      <Input
        id={id}
        placeholder={placeholder}
        value={value}
        maxLength={maxLength}
        onChange={onChange}
      />
      {icon && (
        <RandomButton onClick={onClick}>
          <IconImg src={`/icons/${icon}.svg`} />
        </RandomButton>
      )}
    </Container>
  );
}

const Container = styled.div`
  padding: 8px 16px;
  display: flex;
  align-items: center;
  gap: 8px;
  width: 100%;
  height: 40px;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.bold};
  border-radius: ${({ theme }) => theme.radius.large};
`;

const Label = styled.label`
  width: 64px;
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
  white-space: nowrap;
`;

const RandomButton = styled.button`
  width: 16px;
  height: 16px;
  background-color: transparent;
`;

const IconImg = styled.img`
  width: 100%;
  height: 100%;
`;
