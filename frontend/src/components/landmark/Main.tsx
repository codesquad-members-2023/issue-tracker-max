import { styled } from 'styled-components';

export default function Main({ children }: { children: React.ReactNode }) {
  return (
    <Container>
      <h2 className="blind">메인</h2>
      {children}
    </Container>
  );
}

const Container = styled.main`

`;
