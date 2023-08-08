import styled from "styled-components";
import { Button } from "../Button/Button";
import { Icon } from "components/Common/Icon/Icon";

/* 나중에 추가하기 */
const Indicator = [
  { key: "assignees", value: "담당자" },
  { key: "labels", value: "레이블" },
  { key: "milestones", value: "마일스톤" },
];

export const Sidebar = () => {
  return (
    <Layout>
      <ul>
        {Indicator.map((item) => (
          <li key={item.key}>
            <IndecatorButton size="M" variant="ghost">
              <p>{item.value}</p>
              <Icon icon="Plus" size="M" />
            </IndecatorButton>
          </li>
        ))}
      </ul>
    </Layout>
  );
};

const Layout = styled.div`
  width: 288px;
  border: ${({ theme: { border } }) => border.default};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
  border-radius: ${({ theme }) => theme.radius.large};
  background-color: ${({ theme: { color } }) => color.nuetralSurfaceStrong};

  ul {
    padding: 0;
    li {
      padding: 32px;
      list-style: none;
      cursor: pointer;
      border-bottom: 1px solid
        ${({ theme: { color } }) => color.nuetralBorderDefault};

      &:last-child {
        border: none;
      }
    }
  }
`;

const IndecatorButton = styled(Button)`
  width: 100%;
  width: 100%;
  display: flex;
  justify-content: space-between;
`;
