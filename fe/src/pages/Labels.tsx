import { useLoaderData, useNavigate } from "react-router-dom";
import styled from "styled-components";

import { TabButton, Tab } from "components/Common/Button/TabButton";
import { Button } from "components/Common/Button/Button";
import { Icon } from "components/Common/Icon/Icon";
import { Tag } from "components/Common/Tag/Tag";

interface Label {
  id: number;
  name: string;
  description: null | string;
  backgroundColor: string;
  textColor: string;
}

interface LabelsPageData {
  milestoneCount: number;
  labelCount: number;
  labels: Label[];
}

export const LabelsPage = () => {
  const navigate = useNavigate();
  const data = useLoaderData() as LabelsPageData;
  console.log(data);

  return (
    <Layout>
      <div>
        <Tab activeTab={"레이블"}>
          <TabButton
            icon="Label"
            text="레이블"
            count={data.labelCount}
            onClick={() => navigate("/labels")}
          />
          <TabButton
            icon="Milestone"
            text="마일스톤"
            count={data.milestoneCount}
            onClick={() => navigate("/milestones/open")}
          />
        </Tab>
        <Button size="S" variant="contained">
          <Icon icon="Plus" size="S" stroke="brandTextDefault" />
          레이블 추가
        </Button>
      </div>

      <TableBox>
        <p>{data.labelCount}개의 레이블</p>
        <ul>
          {data.labels.map((label) => (
            <li key={label.id}>
              <LabelBox>
                <Tag
                  text={label.name}
                  color={label.textColor}
                  $backgroundColor={label.backgroundColor}
                  $border
                  size="S"
                />
              </LabelBox>
              <LabelDescriptionBox>{label.description}</LabelDescriptionBox>
              <ButtonsBox>
                <Button size="S" variant="ghost">
                  <Icon icon="Edit" size="S" />
                  편집
                </Button>
                <Button size="S" variant="ghost" states="danger">
                  <Icon icon="Trash" size="S" stroke="dangerTextDefault" />
                  삭제
                </Button>
              </ButtonsBox>
            </li>
          ))}
        </ul>
      </TableBox>
    </Layout>
  );
};

const LabelBox = styled.div`
  width: 176px;
`;
const LabelDescriptionBox = styled.p`
  font: ${({ theme: { font } }) => font.displayM16};
  color: ${({ theme: { color } }) => color.nuetralTextWeak};
  flex-grow: 1;
`;
const ButtonsBox = styled.div`
  display: flex;
  gap: 24px;
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

  > p {
    padding: 20px 32px;
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
    padding: 32px;
    background-color: ${({ theme: { color } }) => color.nuetralSurfaceStrong};
    border-top: 1px solid
      ${({ theme: { color } }) => color.nuetralBorderDefault};
  }
`;
