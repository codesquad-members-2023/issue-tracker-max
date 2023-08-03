import {styled} from "styled-components";
import {IssueData} from "../../page/main/Main";
import {Issue} from "./Issue";

type TableBodyProps = {
  issues: IssueData[];
};

export function IssueTableBody({issues}: TableBodyProps) {
  return (
      <Div>
        {issues.map((issue, index) => (
            <Issue key={index} issue={issue}/>
        ))}
      </Div>
  );
}

const Div = styled.div`
  width: 100%;
`;
