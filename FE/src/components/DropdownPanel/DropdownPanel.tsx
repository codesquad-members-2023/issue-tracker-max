import { styled } from "styled-components";

type Props = {
  title: string;
};

export default function DropdownPanel({ title }: Props) {
  return (
    <Container>
      <Header>
        <Title>{title}</Title>
      </Header>
    </Container>
  );
}

const Container = styled.div`
  width: 240px;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
`;

const Header = styled.div`
  padding: 8px 16px;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.default};
`;

const Title = styled.span`
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;
