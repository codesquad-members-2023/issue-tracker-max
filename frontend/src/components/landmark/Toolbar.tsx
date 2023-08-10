import { styled } from 'styled-components';

export default function Toolbar({ children }: { children: React.ReactNode }) {
  return <Container role="toolbar">{children}</Container>;
}

const Container = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 24px;
  align-items: center;
`;
