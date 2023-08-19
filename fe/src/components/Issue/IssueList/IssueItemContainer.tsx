import { useContext } from 'react';
import { IssueContext } from '../../Context/IssueContext';
import IssueItem from './IssueItem';

export default function IssueItemContainer() {
  const { ...context } = useContext(IssueContext);

  return (
    <ul className="item-container">
      {context.issueList.issues.map((item) => {
        return (
          <IssueItem
            key={item.id}
            issue={item}
            checked={context.checkedItemIdList.includes(item.id)}
          />
        );
      })}
    </ul>
  );
}
