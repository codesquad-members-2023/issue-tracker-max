import { useContext } from 'react';
import { styled } from 'styled-components';
import { Link } from 'react-router-dom';
import { AppContext } from '../main';
import ContextLogo from '../types/ContextLogo';
import ButtonSmall from '../components/common/button/ButtonSmall';
import Layout from '../components/Layout';
import Header from '../components/landmark/Header';
import Main from '../components/landmark/Main';
import CommentElement from '../components/common/CommentElement';
import SideBar from '../components/common/sideBar/SideBar';
import defaultUserImg from '../asset/images/defaultUserImg.png';
import ElementType from '../constant/ElementType';
import TextArea from '../components/common/TextArea';
import InformationTag from '../components/common/InformationTag';

const { AddButton, Assignee, Label } = ElementType;

export default function IssueDetail() {
  const { util } = useContext(AppContext);

  return (
    <Layout>
      <Header>
        <Link to="/">
          <img
            src={(util.getLogoByTheme() as ContextLogo).medium}
            alt="이슈트래커"
          />
        </Link>
        <Profile>
          <img src={defaultUserImg} alt="" />
        </Profile>
      </Header>
      <Body>
        <PostInfo>
          <TitleAndButtons>
            <Title>
              <IssueTitle>FE 이슈트래커 디자인 시스템 구현</IssueTitle>
              <IssueId>#2</IssueId>
            </Title>
            <ButtonSmall type="button" outline iconName="edit">
              제목 편집
            </ButtonSmall>
            <ButtonSmall type="button" outline iconName="archive">
              이슈 닫기
            </ButtonSmall>
          </TitleAndButtons>
          <StateInfo>
            <InformationTag size="medium" iconName="alertCircle">
              열린 이슈
            </InformationTag>
            <span>이 이슈가 3분 전에 fuse123 님에 의해 열렸습니다.</span>
          </StateInfo>
        </PostInfo>
        <Container>
          <CommentArea>
            <CommentElement
              userInfo={{ userName: '퓨즈' }}
              timeStamp="2023-08-15"
              content="하이"
            />
            <CommentElement
              userInfo={{ userName: '제페토' }}
              timeStamp="2023-08-15"
              content="하이요"
            />
            <TextArea name="newComment" placeholder="코멘트를 입력하세요" />
            <ButtonSmall type="submit" disabled iconName="plus">
              코멘트 작성
            </ButtonSmall>
          </CommentArea>
          <RightPanel>
            <SideBar
              sideBarItems={[
                [{ type: AddButton, text: '담당자' }, []],
                [{ type: AddButton, text: '레이블' }, []],
                [{ type: AddButton, text: '마일스톤' }, []],
              ]}
            />
            <ButtonSmall type="button" ghost iconName="trash">
              이슈 삭제
            </ButtonSmall>
          </RightPanel>
        </Container>
      </Body>
    </Layout>
  );
}

const Body = styled(Main)`
  min-width: 1280px;
  display: flex;
  flex-direction: column;
  gap: 24px;
`;

const Profile = styled.div``;

const PostInfo = styled.div`
  padding-bottom: 24px;
  border-bottom: ${({ theme }) => theme.objectStyles.border.default};
  border-bottom-color: ${({ theme }) => theme.color.neutral.border.default};
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 16px;
`;

const TitleAndButtons = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
  align-self: stretch;
`;

const Title = styled.p`
  display: flex;
  align-items: flex-start;
  gap: 8px;
  flex: 1 0 0;
  color: ${({ theme }) => theme.color.neutral.text.strong};
  ${({ theme }) => theme.font.display.bold[32]};
`;

const IssueTitle = styled.span``;

const IssueId = styled.span`
  color: ${({ theme }) => theme.color.neutral.text.weak};
`;

const StateInfo = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  align-self: stretch;
`;

const Container = styled.div`
  display: flex;
  gap: 32px;
`;

const CommentArea = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 24px;
  flex: 1 0 0;
`;

const RightPanel = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
`;
