import {Link} from "react-router-dom";
import styled from "styled-components";
import {Button} from "../components/Button";

export function Error404() {
    return (
        <NotFoundWrapper>
            <ErrorCode>404</ErrorCode>
            <ErrorText>Page Not Found</ErrorText>
            <ButtonWrapper>
                <Link to="/">
                    <Button size="L" buttonType="Ghost">
                        메인으로
                    </Button>
                </Link>
            </ButtonWrapper>
        </NotFoundWrapper>
    );
}

const NotFoundWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100vh;
  height: 100%;
  color: #212529;
`;

const ErrorCode = styled.h1`
  font-size: 10rem;
  margin: 0;
`;

const ErrorText = styled.h2`
  font-size: 2rem;
  margin-bottom: 50px;
`;

const ButtonWrapper = styled.div`
  width: 250px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 2px solid ${({theme}) => theme.color.neutralBorderDefault};
  border-radius: ${({theme}) => theme.radius.large};
`;
