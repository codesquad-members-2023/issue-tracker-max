import styled from "styled-components";
import { useLoaderData, useNavigate } from "react-router-dom";

import { TabButton, Tab } from "components/Common/Button/TabButton";
import { Button } from "components/Common/Button/Button";
import { Icon } from "components/Common/Icon/Icon";
import { ProgressBar } from "components/Common/Bar/ProgressBar";

interface Milestone {
  id: number;
  name: string;
  description: string | null;
  dueDate: string;
  openIssueCount: number;
  closedIssuesCount: number;
}

interface MilestonesPageData {
  openMilestoneCount: number;
  closeMilestoneCount: number;
  labelCount: number;
  milestones: Milestone[];
}

export const MilestonesPage = () => {
  const navigate = useNavigate();
  const data = useLoaderData() as MilestonesPageData;
  console.log(data);

  return (
    <Layout>
      <div>
        <Tab activeTab={"마일스톤"}>
          <TabButton
            icon="Label"
            text="레이블"
            count={data.labelCount}
            onClick={() => navigate("/labels")}
          />
          <TabButton
            icon="Milestone"
            text="마일스톤"
            count={data.milestones.length}
            onClick={() => navigate("/milestones/open")}
          />
        </Tab>
        <Button size="S" variant="contained">
          <Icon icon="Plus" size="S" stroke="brandTextDefault" />
          마일스톤 추가
        </Button>
      </div>

      <TableBox>
        <div>
          <Button size="M" variant="ghost">
            <Icon icon="AlertCircle" size="M" />
            열린 마일스톤({data.openMilestoneCount})
          </Button>
          <Button size="M" variant="ghost">
            <Icon icon="Archive" size="M" />
            닫힌 마일스톤({data.closeMilestoneCount})
          </Button>
        </div>

        <ul>
          {data.milestones.map((milestone) => (
            <li key={milestone.id}>
              <MilestoneInfoBox>
                <div>
                  <NameBox>
                    <Icon icon="Milestone" size="S" fill="paletteBlue" />
                    <p>{milestone.name}</p>
                  </NameBox>
                  <DateBox>
                    <Icon icon="Calendar" size="S" stroke="nuetralTextWeak" />
                    <span>{milestone.dueDate}</span>
                  </DateBox>
                </div>
                <DescriptionBox>{milestone.description}</DescriptionBox>
              </MilestoneInfoBox>
              <div>
                <ButtonsBox>
                  <Button size="S" variant="ghost">
                    <Icon icon="Archive" size="S" />
                    닫기
                  </Button>
                  <Button size="S" variant="ghost">
                    <Icon icon="Edit" size="S" />
                    편집
                  </Button>
                  <Button size="S" variant="ghost" states="danger">
                    <Icon icon="Trash" size="S" stroke="dangerTextDefault" />
                    삭제
                  </Button>
                </ButtonsBox>
                <ProgressBar
                  openIssueCount={milestone.openIssueCount}
                  closedIssuesCount={milestone.closedIssuesCount}
                />
              </div>
            </li>
          ))}
        </ul>
      </TableBox>
    </Layout>
  );
};

const MilestoneInfoBox = styled.div`
  display: flex;
  flex-direction: column;

  div {
    display: flex;
    align-items: center;
    gap: 8px;
  }
`;
const NameBox = styled.div`
  font: ${({ theme: { font } }) => font.availableM16};
  color: ${({ theme: { color } }) => color.neutralTextStrong};
  margin-right: 8px;
`;
const DateBox = styled.div`
  font: ${({ theme: { font } }) => font.displayM12};
  color: ${({ theme: { color } }) => color.nuetralTextWeak};
`;
const DescriptionBox = styled.p`
  font: ${({ theme: { font } }) => font.displayM16};
  color: ${({ theme: { color } }) => color.nuetralTextWeak};
  height: 24px;
`;

const ButtonsBox = styled.div`
  display: flex;
  gap: 24px;
  height: 32px;
  margin-bottom: 8px;
  justify-content: flex-end;
`;

const Layout = styled.div`
  margin-top: 30px;
  display: flex;
  flex-direction: column;
  gap: 24px;

  > :first-child {
    display: flex;
    justify-content: space-between;
    background-color: ${({ theme: { color } }) => color.nuetralSurfaceDefault};
  }
`;

const TableBox = styled.div`
  border: ${({ theme: { border } }) => border.default};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
  border-radius: ${({ theme: { radius } }) => radius.large};

  > div {
    display: flex;
    padding: 4px 32px;
    gap: 24px;
    font: ${({ theme: { font } }) => font.displayB16};
    color: ${({ theme: { color } }) => color.nuetralTextDefault};
  }

  ul {
    padding: 0;
  }
  li {
    display: flex;
    gap: 32px;
    align-items: center;
    justify-content: space-between;
    list-style: none;
    padding: 16px 32px;
    background-color: ${({ theme: { color } }) => color.nuetralSurfaceStrong};
    border-top: 1px solid
      ${({ theme: { color } }) => color.nuetralBorderDefault};
  }
`;
