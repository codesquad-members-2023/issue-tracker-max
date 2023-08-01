import { styled } from 'styled-components';
import Icons from '../../design/Icons';

enum ElementType {
  AddButton,
  Assignee,
  Label,
  milestone,
}

const { AddButton, Assignee, Label, milestone } = ElementType;

type ElementProps<T extends ElementType> = {
  type: T;
} & (T extends typeof AddButton
  ? { text: string }
  : T extends typeof Assignee
  ? { text: string; checked: boolean }
  : T extends typeof Label
  ? { text: string }
  : { text: string; progress: number });

const dummy: ElementProps<
  typeof AddButton | typeof Assignee | typeof Label | typeof milestone
>[] = [
  { type: AddButton, text: 'label1' },
  { type: Assignee, text: 'label2', checked: false },
  { type: Label, text: 'label3' },
  { type: milestone, text: 'label4', progress: 30 },
];

export default function SideBar() {
  return (
    <Container>
      <h2 className="blind">사이드바</h2>
      <ul>
        {dummy.map((props) => (
          <li key={props.text}>
            <Element type={props.type} props={props} />
          </li>
        ))}
      </ul>
    </Container>
  );
}

const Container = styled.aside`
  width: 288px;
  border: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  overflow: hidden;
  border-radius: ${({ theme }) => theme.objectStyles.radius.large};
  & > ul {
    & > li {
      padding: 32px;
      border-bottom: 1px solid
        ${({ theme }) => theme.color.neutral.border.default};
      &:last-child {
        border: 0;
      }
      & > button {
        width: 100%;
        border: 0;
        background-color: transparent;
        display: inline-flex;
        align-items: center;
        justify-content: space-between;
        color: ${({ theme }) => theme.color.neutral.text.default};
        ${({ theme }) => theme.font.available.medium[16]};
        & > svg {
          & > path {
            stroke: ${({ theme }) => theme.color.neutral.text.default};
          }
        }
      }

      & > label {
        width: 100%;
        display: inline-flex;
        gap: 8px;
        align-items: center;
        ${({ theme }) => theme.font.display.medium[12]};
        & > input {
          display: none;
        }
        & > span {
          display: inline-block;
          color: ${({ theme }) => theme.color.neutral.text.strong};
          width: 100%;
        }
      }
      & > span {
        border: 1px solid ${({ theme }) => theme.color.neutral.border.default};
        ${({ theme }) => theme.font.display.medium[12]};
        border-radius: ${({ theme }) => theme.objectStyles.radius.large};
        color: ${({ theme }) => theme.color.neutral.text.weak};
        height: 24px;
        display: inline-flex;
        align-items: center;
        padding: 0 8px;
      }
      & > article {
        & > progress {
          width: 100%;
          margin-bottom: 4px;
        }
        & > label {
          ${({ theme }) => theme.font.display.medium[12]};
          color: ${({ theme }) => theme.color.neutral.text.strong};
        }
      }
    }
  }
`;

function Element<T extends ElementType>({
  type,
  props,
}: {
  type: T;
  props: ElementProps<T>;
}) {
  const {
    text,
    checked,
    progress,
  }: { text: string; checked?: boolean; progress?: number } = props;
  switch (type) {
    case AddButton:
      return (
        <button>
          <span>{text}</span>
          <Icons.plus />
        </button>
      );
    case Assignee:
      return (
        <label htmlFor={text}>
          <Icons.userImageLarge />
          <span>{text}</span>
          <input type="checkbox" name={text} id={text} checked={checked} />
        </label>
      );
    case Label:
      return <span>{text}</span>;
    case milestone:
      return (
        <article>
          <h4 className="blind">{text} 진행률</h4>
          <progress id={text} value={progress} max="100">
            {progress}%
          </progress>
          <br />
          <label htmlFor={text}>{text}</label>
        </article>
      );
  }
}
