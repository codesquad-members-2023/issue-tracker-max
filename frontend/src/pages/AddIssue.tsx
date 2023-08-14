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
import defaultUserImg from '../asset/images/defaultUserImg.png';

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
        <Profile>
          <img src={defaultUserImg} alt="" />
        </Profile>
      </Header>
      <Body>
        <Title>새로운 이슈 작성</Title>
        <Container>
          <UserImg src={defaultUserImg} alt="" />
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
          <Button type="button" ghost flexible iconName="xSquare">
            작성 취소
          </Button>
          <ButtonLarge type="submit">완료</ButtonLarge>
        </ButtonArea>
      </Body>
    </Layout>
  );
}

const Profile = styled.div``;

const Body = styled(Main)`
  min-width: 1280px;
  display: flex;
  flex-direction: column;
  gap: 24px;
`;

const Title = styled.p`
  padding-bottom: 24px;
  border-bottom: ${({ theme }) => theme.objectStyles.border.default};
  border-bottom-color: ${({ theme }) => theme.color.neutral.border.default};
  color: ${({ theme }) => theme.color.neutral.text.strong};
  ${({ theme }) => theme.font.display.bold[32]};
`;

const Container = styled.div`
  padding-bottom: 24px;
  display: flex;
  align-items: flex-start;
  flex: 1 0 0;
  gap: 24px;
  border-bottom: ${({ theme }) => theme.objectStyles.border.default};
  border-bottom-color: ${({ theme }) => theme.color.neutral.border.default};
`;

const UserImg = styled.img``;

const InputArea = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  flex: 1 0 0;

  & > div {
    height: 552px;
  }
`;

const ButtonArea = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 32px;
`;
