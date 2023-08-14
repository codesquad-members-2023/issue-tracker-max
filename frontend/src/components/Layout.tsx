import { styled } from 'styled-components';

export default function Layout({ children }: { children: React.ReactNode }) {
  return <Container>{children}</Container>;
}

const Container = styled.div`
  padding: 0 80px;
  max-width: 1440px;
  margin: auto;
`;
