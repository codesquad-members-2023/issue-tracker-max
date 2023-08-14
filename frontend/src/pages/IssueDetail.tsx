import { useContext } from 'react';
import { styled } from 'styled-components';
import { Link } from 'react-router-dom';
import { AppContext } from '../main';
import ContextLogo from '../types/ContextLogo';
import ButtonSmall from '../components/common/button/ButtonSmall';
import Layout from '../components/Layout';
import Header from '../components/landmark/Header';
import Main from '../components/landmark/Main';
import defaultUserImg from '../asset/images/defaultUserImg.png';

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
      <Main>
        <PostInfo>
          <TitleAndButtons>
            <IssueTitle>FE 이슈트래커 디자인 시스템 구현 #2</IssueTitle>
            <ButtonSmall type="button" outline>
              제목 편집
            </ButtonSmall>
            <ButtonSmall type="button" outline>
              이슈 닫기
            </ButtonSmall>
          </TitleAndButtons>
          <StateInfo></StateInfo>
        </PostInfo>
        <Container></Container>
      </Main>
    </Layout>
  );
}

const Profile = styled.div``;

const PostInfo = styled.div``;

const TitleAndButtons = styled.div``;

const IssueTitle = styled.p``;

const StateInfo = styled.div``;

const Container = styled.div``;
