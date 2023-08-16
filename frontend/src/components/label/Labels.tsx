import { styled } from 'styled-components';
import LabelsItem from './LabelsItem';
import LabelItemProps from '../../types/LabelItemProps';

export default function Labels({ data }: { data: LabelItemProps[] }) {
  return (
    <Container>
      <Header>{data.length}개의 레이블</Header>
      <Body>
        {data.map((ItemProps) => (
          <li key={ItemProps.name + ItemProps.id}>
            <LabelsItem {...ItemProps} />
          </li>
        ))}
      </Body>
    </Container>
  );
}
const Header = styled.h2`
  padding: 20px 32px;
  background: ${({ theme }) => theme.color.neutral.surface.default};
  border-bottom: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  ${({ theme }) => theme.font.display.bold[16]}
`;

const Body = styled.ul`
  & > li {
    border-bottom: 1px solid
      ${({ theme }) => theme.color.neutral.border.default};

    &:last-child {
      border: 0;
    }
  }
`;

const Container = styled.article`
  border-radius: ${({ theme }) => theme.objectStyles.radius.large};
  border: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  overflow: hidden;
  margin: auto;
`;
