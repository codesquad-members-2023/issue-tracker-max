import { styled } from 'styled-components';
import Icons from '../../design/Icons';

enum Option {
  Available,
  Selected,
}

const dummy = [
  ['label', Option.Selected],
  ['label', Option.Available],
] as [string, Option][];

export default function DropdownPanel({ label }: { label: string }) {
  return (
    <Container>
      <Header>{label}</Header>
      <DropdownElements>
        {dummy.map(([text, option]) => {
          const Icon = Icons.userImageSmall;
          return (
            <li key={text}>
              <Element htmlFor={text} $option={option}>
                <Icon />
                <Text>{text}</Text>
                <input
                  type="checkbox"
                  id={text}
                  checked={option ? true : false}
                />
                {option ? <Icons.checkOnCircle /> : <Icons.checkOffCircle />}
              </Element>
            </li>
          );
        })}
      </DropdownElements>
    </Container>
  );
}

const Container = styled.article`
  width: 240px;
  border-radius: ${({ theme }) => theme.objectStyles.radius.large};
  border: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  overflow: hidden;
`;

const Header = styled.h3`
  padding: 8px 16px;
  color: ${({ theme }) => theme.color.neutral.text.weak};
  border-bottom: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  background-color: ${({ theme }) => theme.color.neutral.surface.default};
  ${({ theme }) => theme.font.display.medium[12]}
`;

const DropdownElements = styled.ul`
  & > li {
    padding: 8px 16px;
    display: flex;
    align-items: center;
    border-bottom: 1px solid
      ${({ theme }) => theme.color.neutral.border.default};
  }
  & > li:last-child {
    border: 0;
  }
`;

const Element = styled.label<{ $option: Option }>`
  width: 100%;
  display: flex;
  align-items: center;
  gap: 8px;
  ${({ theme, $option }) =>
    $option ? theme.font.selected.bold[16] : theme.font.available.medium[16]}
  color: ${({ theme, $option }) =>
    $option
      ? theme.color.neutral.text.strong
      : theme.color.neutral.text.default};
`;

const Text = styled.span`
  width: 100%;
`;
