import { styled } from 'styled-components';

export default function Main({
  className,
  children,
}: {
  className?: string;
  children: React.ReactNode;
}) {
  return (
    <Container className={className}>
      <h2 className="blind">메인</h2>
      {children}
    </Container>
  );
}

const Container = styled.main`
  display: flex;
  flex-direction: column;
  gap: 24px;
`;
