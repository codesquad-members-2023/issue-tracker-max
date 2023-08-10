import styled from "styled-components";
import { Button } from "components/Common/Button/Button";
import { useNavigate } from "react-router-dom";

export const PageNotFound = () => {
  const navigate = useNavigate();

  return (
    <Layout>
      <h2>WRONG PAGE</h2>
      <Button size="L" variant="ghost" onClick={() => navigate(-1)}>
        뒤로 가기
      </Button>
      <Button size="L" variant="ghost" onClick={() => navigate("/")}>
        홈으로 돌아가기
      </Button>
    </Layout>
  );
};

const Layout = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  h2 {
    font: ${({ theme: { font } }) => font.displayB32};
    margin-bottom: 32px;
  }
`;
