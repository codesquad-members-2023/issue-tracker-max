import { styled } from 'styled-components';
import ElementType from '../../../constant/ElementType';
import Icons from '../../../design/Icons';
import ElementProps from '../../../types/ElementProps';
import Label from '../../label/Label';

export default function Element<T extends ElementType>({
  type,
  props,
}: {
  type: T;
  props: ElementProps<T>;
}) {
  switch (type) {
    case ElementType.AddButton:
      return (() => {
        const { text } = props as ElementProps<ElementType.AddButton>;
        return (
          <AddButton>
            <span>{text}</span>
            <Icons.plus />
          </AddButton>
        );
      })();
    case ElementType.Assignee:
      return (() => {
        const { text, checked } = props as ElementProps<ElementType.Assignee>;
        return (
          <Assignee htmlFor={text}>
            <Icons.userImageLarge />
            <span>{text}</span>
            <input type="checkbox" name={text} id={text} checked={checked} />
          </Assignee>
        );
      })();
    case ElementType.Label:
      return (() => {
        const { labelProps } = props as ElementProps<ElementType.Label>;
        return <Label {...labelProps} />;
      })();
    case ElementType.milestone:
      return (() => {
        const { text, progress } = props as ElementProps<ElementType.milestone>;
        return (
          <Milestone>
            <h4 className="blind">{text} 진행률</h4>
            <progress id={text} value={progress} max="100">
              {progress}%
            </progress>
            <br />
            <label htmlFor={text}>{text}</label>
          </Milestone>
        );
      })();
  }
}

const AddButton = styled.button`
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
`;

const Assignee = styled.label`
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
`;

const Milestone = styled.article`
  & > progress {
    width: 100%;
    margin-bottom: 4px;
  }
  & > label {
    ${({ theme }) => theme.font.display.medium[12]};
    color: ${({ theme }) => theme.color.neutral.text.strong};
  }
`;
