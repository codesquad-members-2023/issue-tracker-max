import { styled } from 'styled-components';
import InformationTag from '../common/InformationTag';
import LabelProps from '../../types/LabelProps';

export default function Label({
  textColor,
  backgroundColor,
  name,
}: LabelProps) {
  return (
    <Container $textColor={textColor} $backgroundColor={backgroundColor}>
      <InformationTag size="small">{name}</InformationTag>
    </Container>
  );
}

const Container = styled.span<{ $textColor: string; $backgroundColor: string }>`
  flex-shrink: 0;
  display: inline-block;
  border-radius: ${({ theme }) => theme.objectStyles.radius.large};
  background: ${({ $backgroundColor }) => $backgroundColor};
  & > span > span {
    color: ${({ $textColor }) => $textColor};
  }
`;
