import { styled } from 'styled-components';

export default function Header({ children }: { children: React.ReactNode }) {
  return (
    <Container>
      <h1 className="blind">헤더</h1>
      {children}
    </Container>
  );
}

const Container = styled.header`
  height: 94px;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 32px;
`;
