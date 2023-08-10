import { useEffect, useRef } from "react";
import { styled } from "styled-components";
import { Button } from "../../components/Button";
import { Icon } from "../../components/icon/Icon";

export function SignUpSuccessModal({ onClick }: { onClick: () => void }) {
  const modalRef = useRef<HTMLDialogElement>(null);

  useEffect(() => {
    modalRef.current?.showModal();
  }, []);

  return (
    <Dialog ref={modalRef}>
      <Icon name="success" />
      <Title>회원가입 되었습니다!</Title>
      <span>로그인 버튼을 눌러서 로그인 화면으로 이동해 주세요.</span>
      <Button size="M" buttonType="Container" onClick={onClick}>
        로그인
      </Button>
    </Dialog>
  );
}

const Dialog = styled.dialog`
  width: 400px;
  height: 700px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 40px;
  border: none;
`;

const Title = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  color: ${({ theme }) => theme.color.neutralTextStrong};
  font: ${({ theme }) => theme.font.displayBold32};
`;
