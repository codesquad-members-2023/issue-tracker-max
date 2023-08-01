import styled from "styled-components";
import {
  IssueListHeader,
  ChangeStateHeader,
} from "components/Header/IssueListHeader";

export const MainPage = () => {
  return (
    <Content>
      <IssueListHeader />
      <ChangeStateHeader />
      {/* <IssueTable/> */}
    </Content>
  );
};

const Content = styled.div`
  border: ${({ theme: { border } }) => border.default};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
  border-radius: ${({ theme: { radius } }) => radius.large};
`;
