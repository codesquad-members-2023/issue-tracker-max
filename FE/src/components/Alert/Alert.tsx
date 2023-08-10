import { styled } from "styled-components";
import Button from "../common/Button/Button";

type Props = {
  onClickCancel(): void;
  onClickActive(): void;
};

export default function Alert({ onClickCancel, onClickActive }: Props) {
  return (
    <Container>
      <AlertContent>정말 삭제하시겠습니까?</AlertContent>
      <ButtonTap>
        <Button
          type={"outline"}
          label={"취소"}
          width={"176px"}
          onClick={onClickCancel}
        />
        <Button label={"삭제"} width={"176px"} onClick={onClickActive} />
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
