import { styled } from "styled-components";

export default function Alert() {
  return (
    <Container>
      <AlertContent></AlertContent>
      <ButtonTap></ButtonTap>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  flex-direction: column;
  padding: 32px;
  width: 424px;
  height: 180px;
`;

const AlertContent = styled.p``;

const ButtonTap = styled.div``;
