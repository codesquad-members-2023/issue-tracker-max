import ElementType from '../../../constant/ElementType';
import ElementProps from '../../../types/ElementProps';
import Element from './Element';

const { Assignee, Label, milestone } = ElementType;

export default function Elements({
  items,
}: {
  items: (
    | ElementProps<typeof Assignee>
    | ElementProps<typeof Label>
    | ElementProps<typeof milestone>
  )[];
}) {
  return (
    <ul>
      {items.map((props, index) => (
        <li key={index}>
          <Element type={props.type} props={props} />
        </li>
      ))}
    </ul>
  );
}
