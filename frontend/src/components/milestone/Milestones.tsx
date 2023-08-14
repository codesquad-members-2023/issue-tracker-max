import { styled } from 'styled-components';
import Button from '../common/button/BaseButton';
import { useState } from 'react';
import Milestone from './Milestone';

enum MilestoneType {
  open,
  close,
}

const { open, close } = MilestoneType;

type Milestone = {
  id: number;
  name: string;
  description: string;
  doneDate: string;
  closedIssueCount: number;
  openIssueCount: number;
};

export default function Milestones({ data }: { data: Milestone[] }) {
  const [milestoneType, setMilestoneType] = useState<MilestoneType>(open);

  return (
    <Container>
      <Header>
        <Buttons>
          <li>
            <Button
              iconName="alertCircle"
              type="button"
              selected={milestoneType === open}
              ghost
              flexible>
              열린 마일스톤
              {getOpenMilestoneCount(data)
                ? ` (${getOpenMilestoneCount(data)})`
                : ''}
            </Button>
          </li>
          <li>
            <Button
              iconName="archive"
              type="button"
              selected={milestoneType === close}
              ghost
              flexible>
              닫힌 마일스톤
              {getClosedMilestoneCount(data)
                ? ` (${getClosedMilestoneCount(data)})`
                : ''}
            </Button>
          </li>
        </Buttons>
      </Header>
      <Body>
        {data.map((milestone) => (
          <TableElement key={milestone.name + milestone.id}>
            <Milestone {...{ ...milestone }} />
          </TableElement>
        ))}
      </Body>
    </Container>
  );
}

const Container = styled.article`
  border-radius: ${({ theme }) => theme.objectStyles.radius.large};
  border: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  overflow: hidden;
  margin: auto;
`;

const Header = styled.h2`
  padding: 20px 32px;
  background: ${({ theme }) => theme.color.neutral.surface.default};
  border-bottom: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  ${({ theme }) => theme.font.display.bold[16]}
`;

const Buttons = styled.menu`
  display: flex;
  gap: 24px;
`;

const Body = styled.ul``;

const TableElement = styled.li`
  border-bottom: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  &:last-child {
    border-bottom: 0;
  }
`;

function getOpenMilestoneCount(data: Milestone[]) {
  return data.filter(({ closedIssueCount, openIssueCount }) => {
    const total = closedIssueCount + openIssueCount;
    if (!total) {
      return false;
    }
    return closedIssueCount / total < 1;
  }).length;
}

function getClosedMilestoneCount(data: Milestone[]) {
  return data.filter(({ closedIssueCount, openIssueCount }) => {
    const total = closedIssueCount + openIssueCount;
    if (!total) {
      return true;
    }
    return closedIssueCount === total;
  }).length;
}
