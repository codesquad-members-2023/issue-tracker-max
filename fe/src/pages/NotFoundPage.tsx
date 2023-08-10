import sadness from "@assets/sadness.png";
import Button from "@components/common/Button";
import styled from "styled-components";

export default function NotFoundPage() {
  return (
    <StyledNotFound>
      <img src={sadness} alt="" width="50%" height="70%" />
      <div className="wrapper">
        <Text>Oops...</Text>
        <Text>404 Page Not Found</Text>
        <Text>요청하신 페이지를 찾을 수 없습니다.</Text>
        <Button variant="container" size="L" onClick={() => history.go(-1)}>
          <span>이전 화면으로 돌아가기</span>
        </Button>
      </div>
    </StyledNotFound>
  );
}

const StyledNotFound = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  .wrapper {
    display: flex;
    gap: 2rem;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  span {
    font: ${({ theme: { font } }) => font.displayBold16};
  }
`;

const Text = styled.div`
  font: ${({ theme: { font } }) => font.displayBold32};
  color: ${({ theme: { neutral } }) => neutral.text.default};
`;
