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
    <Dim>
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
    </Dim>
  );
}

const Dim = styled.div`
  position: absolute;
  top: 0px;
  left: 0px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  z-index: 1000;
  background-color: rgba(16, 20, 26, 0.4);
`;

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

const AlertContent = styled.p`
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.default};
`;

const ButtonTap = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-around;
`;
