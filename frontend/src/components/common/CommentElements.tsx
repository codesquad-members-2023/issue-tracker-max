import { useState } from 'react';
import { styled } from 'styled-components';
import InformationTag from './InformationTag';
import ButtonSmall from './button/ButtonSmall';
import DefaultUserImg from '../../asset/icons/userImageLarge.svg';

type CommentProps = {
  userInfo: userInfoType;
  timeStamp: string;
};

type userInfoType = {
  userImg?: string;
  userName: string;
};

export default function Comment(props: CommentProps) {
  const [isEdit, setIsEdit] = useState<boolean>(false);

  const { userInfo, timeStamp } = props;

  return (
    <Wrapper>
      <Header>
        <InfoLeft>
          {userInfo.userImg ? (
            <UserImg src={userInfo.userImg}></UserImg>
          ) : (
            <UserImg src={DefaultUserImg}></UserImg>
          )}
          <UserName>{userInfo.userName}</UserName>
          <TimeStamp>{timeStamp}</TimeStamp>
        </InfoLeft>
        <InfoRight>
          <InformationTag size="small">작성자</InformationTag>
          <ButtonSmall type="button" ghost flexible iconName="edit">
            편집
          </ButtonSmall>
          <ButtonSmall type="button" ghost flexible iconName="smile">
            반응
          </ButtonSmall>
        </InfoRight>
      </Header>
      <Body>
        <TextArea></TextArea>
      </Body>
      {isEdit && (
        <Footer>
          <ButtonSmall type="button" ghost flexible iconName="paperClip">
            파일 첨부하기
          </ButtonSmall>
        </Footer>
      )}
    </Wrapper>
  );
}

const Wrapper = styled.div`
  width: 640px;
  min-height: 184px;
  display: flex;
  flex-direction: column;
  border: ${({ theme }) => theme.objectStyles.border.default};
  border-color: ${({ theme }) => theme.color.neutral.border.default};
  border-radius: ${({ theme }) => theme.objectStyles.radius.large};
  background: ${({ theme }) => theme.color.neutral.surface.strong};
`;

const Header = styled.header`
  padding: 16px 24px;
  display: flex;
  justify-content: space-between;
  border-bottom: ${({ theme }) => theme.objectStyles.border.default};
  border-color: ${({ theme }) => theme.color.neutral.border.default};
  border-radius: ${({ theme }) => theme.objectStyles.radius.large}
    ${({ theme }) => theme.objectStyles.radius.large} 0 0;
  background: ${({ theme }) => theme.color.neutral.surface.default};
`;

const InfoLeft = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;

const UserImg = styled.img``;
const UserName = styled.span`
  color: ${({ theme }) => theme.color.neutral.text.default};
  ${({ theme }) => theme.font.display.medium[16]}
`;
const TimeStamp = styled.span`
  color: ${({ theme }) => theme.color.neutral.text.weak};
  ${({ theme }) => theme.font.display.medium[16]}
`;

const InfoRight = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
`;
const Body = styled.div``;
const TextArea = styled.textarea``;
const Footer = styled.footer``;
