import { styled } from 'styled-components';
import ElementType from '../../../constant/ElementType';
import ElementProps from '../../../types/ElementProps';
import Elements from './Elements';
import Element from './Element';

const { AddButton, Assignee, Label, milestone } = ElementType;

type SideBarProps = {
  sideBarItems: [
    ElementProps<typeof AddButton>,
    (
      | ElementProps<typeof Assignee>
      | ElementProps<typeof Label>
      | ElementProps<typeof milestone>
    )[]
  ][];
};

export default function SideBar({ sideBarItems }: SideBarProps) {
  return (
    <Container>
      <h2 className="blind">사이드바</h2>
      <ul>
        {sideBarItems.map(([props, items]) => (
          <li key={props.text}>
            <Element type={props.type} props={props} />
            <Elements items={items} />
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
    }
  }
`;
