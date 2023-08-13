import { styled } from "styled-components";
import Button from "../common/Button/Button";

type Props = {
  content: string;
  leftButtonLabel: string;
  rightButtonLabel: string;
  onClickLeftButton(): void;
  onClickRightButton(): void;
};

export default function Alert({
  content,
  leftButtonLabel,
  rightButtonLabel,
  onClickLeftButton,
  onClickRightButton,
}: Props) {
  return (
    <Container>
      <AlertContent>{content}</AlertContent>
      <ButtonTap>
        <Button
          type={"outline"}
          label={leftButtonLabel}
          width={"176px"}
          onClick={onClickLeftButton}
        />
        <Button
          label={rightButtonLabel}
          width={"176px"}
          onClick={onClickRightButton}
        />
      </ButtonTap>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 32px;
  width: 424px;
  height: 180px;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.strong};
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.large};
  box-shadow: ${({ theme }) => theme.dropShadow.lightMode};
`;

const AlertContent = styled.p``;

const ButtonTap = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-around;
`;
