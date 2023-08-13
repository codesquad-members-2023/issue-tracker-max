import { useContext } from 'react';
import { styled } from 'styled-components';
import { Link } from 'react-router-dom';
import { AppContext } from '../main';
import ContextLogo from '../types/ContextLogo';
import Layout from '../components/Layout';
import Header from '../components/landmark/Header';
import Main from '../components/landmark/Main';
import Button from '../components/common/button/BaseButton';
import ButtonLarge from '../components/common/button/ButtonLarge';
import SideBar from '../components/common/SideBar';
import TextInput from '../components/common/TextInput';
import TextArea from '../components/common/TextArea';

export default function AddIssue() {
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
        <Profile>유저 프로필</Profile>
      </Header>
      <Main>
        <h2>새로운 이슈 작성</h2>
        <Container>
          <InputArea>
            <TextInput
              size="tall"
              name="issueTitle"
              value=""
              placeholder="제목"></TextInput>
            <TextArea
              name="issueContent"
              value=""
              placeholder="코멘트를 입력하세요"></TextArea>
          </InputArea>
          <SideBar></SideBar>
        </Container>
        <ButtonArea>
          <Button type="button" ghost iconName="xSquare">
            작성 취소
          </Button>
          <ButtonLarge type="submit">완료</ButtonLarge>
        </ButtonArea>
      </Main>
    </Layout>
  );
}

const Profile = styled.div``;

const Container = styled.div``;

const InputArea = styled.div``;

const ButtonArea = styled.div``;
