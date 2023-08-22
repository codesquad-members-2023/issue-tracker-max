import { styled } from 'styled-components';
import Icons from '../../design/Icons';
import ProgressIndicator from '../common/ProgressIndicator';
import Button from '../common/button/BaseButton';
import MilestoneProps from '../../types/MilestoneProps';
import { useState } from 'react';
import EditMilestone from './EditMilestone';

export default function Milestone({
  id,
  name,
  description,
  doneDate,
  closedIssueCount,
  openIssueCount,
}: MilestoneProps) {
  const [isEdit, setIsEdit] = useState(false);

  if (isEdit) {
    return (
      <Container>
        <EditMilestone
          {...{
            id,
            name,
            description,
            doneDate,
            closedIssueCount,
            openIssueCount,
            cancel: () => {
              setIsEdit(false);
            },
          }}
        />
      </Container>
    );
  }

  return (
    <Container>
      <Body>
        <MilestoneInfo>
          <Title>
            <Icons.milestone />
            <span>{name}</span>
          </Title>
          <CompletionDate>
            <Icons.calender />
            <time dateTime={doneDate}>{doneDate.replace(/[-]/g, '.')}</time>
          </CompletionDate>
        </MilestoneInfo>
        <Description>{description}</Description>
      </Body>
      <Right>
        <RightButton>
          <li>
            <Button type="button" iconName="archive" ghost flexible>
              닫기
            </Button>
          </li>
          <li>
            <Button
              type="button"
              iconName="edit"
              ghost
              flexible
              onClick={() => setIsEdit(true)}>
              편집
            </Button>
          </li>
          <li>
            <Button type="button" iconName="trash" ghost flexible>
              삭제
            </Button>
          </li>
        </RightButton>
        <ProgressIndicator
          openCount={openIssueCount}
          closeCount={closedIssueCount}
        />
      </Right>
    </Container>
  );
}

const Container = styled.div`
  background: ${({ theme }) => theme.color.neutral.surface.strong};
  padding: 24px 32px;
  display: flex;
  gap: 40px;
`;

const Body = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  width: 100%;
`;

const MilestoneInfo = styled.div`
  display: flex;
  gap: 16px;
  align-items: center;
`;

const CompletionDate = styled.div`
  display: flex;
  gap: 8px;
  & > time {
    ${({ theme }) => theme.font.display.medium[12]};
    color: ${({ theme }) => theme.color.neutral.text.weak};
  }
`;

const Title = styled.h4`
  ${({ theme }) => theme.font.available.medium[16]};
  display: flex;
  align-items: center;
  gap: 4px;
  & > svg {
    path {
      fill: ${({ theme }) => theme.color.palette.blue};
    }
  }
`;

const Description = styled.p`
  font: ${({ theme }) => theme.font.display.medium[16]};
  color: ${({ theme }) => theme.color.neutral.text.weak};
`;

const Right = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  align-items: flex-end;
`;

const RightButton = styled.menu`
  display: inline-flex;
  gap: 24px;
  & > li:last-child button {
    color: ${({ theme }) => theme.color.danger.text.default};

    path {
      stroke: ${({ theme }) => theme.color.danger.text.default};
    }
  }
`;
