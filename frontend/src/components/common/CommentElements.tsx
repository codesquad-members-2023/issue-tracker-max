import { useState, useEffect } from 'react';
import { styled } from 'styled-components';
import InformationTag from './InformationTag';
import ButtonSmall from './button/ButtonSmall';
import DefaultUserImg from '../../asset/icons/userImageLarge.svg';

type CommentProps = {
  userInfo: userInfoType;
  timeStamp: string;
  comment: string;
};

type userInfoType = {
  userImg?: string;
  userName: string;
};

export default function Comment(props: CommentProps) {
  const [isEdit, setIsEdit] = useState<boolean>(false);
  const [isTyping, setIsTyping] = useState<boolean>(false);
  const [textValue, setTextValue] = useState<string>(props.comment);

  useEffect(() => {
    if (isEdit && !isTyping) {
      setIsTyping(true);
      setTimeout(() => {
        setIsTyping(false);
      }, 2000);
    }
  }, [textValue]);

  const { userInfo, timeStamp } = props;

  const handleEdit = () => {
    isEdit ? setIsEdit(false) : setIsEdit(true);
  };

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    if (!isEdit) {
      return;
    }
    setTextValue(e.target.value);

    return;
  };

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
          <ButtonSmall
            type="button"
            ghost
            flexible
            iconName="edit"
            onClick={() => {
              handleEdit();
            }}>
            편집
          </ButtonSmall>
          <ButtonSmall type="button" ghost flexible iconName="smile">
            반응
          </ButtonSmall>
        </InfoRight>
      </Header>
      <Body>
        {isEdit ? (
          <TextArea
            $isEdit={isEdit}
            value={textValue}
            onChange={handleChange}></TextArea>
        ) : (
          <TextBox>{textValue}</TextBox>
        )}
      </Body>
      {isEdit && (
        <Bottom>
          {isTyping && (
            <TextCounter>띄어쓰기 포함 {textValue.length}자</TextCounter>
          )}
          <ButtonSmall type="button" ghost flexible iconName="paperClip">
            파일 첨부하기
          </ButtonSmall>
        </Bottom>
      )}
    </Wrapper>
  );
}

const Wrapper = styled.div`
  min-width: 640px;
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

const UserImg = styled.img`
  width: 32px;
  height: 32px;
  border-radius: ${({ theme }) => theme.objectStyles.radius.half};
`;
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
const Body = styled.div`
  padding: 16px 24px 24px 24px;
  display: flex;
  align-items: flex-start;
  gap: 10px;
`;
const TextBox = styled.div`
  color: ${({ theme }) => theme.color.neutral.text.default};
  ${({ theme }) => theme.font.display.medium[16]}
`;

const TextArea = styled.textarea<{ $isEdit: boolean }>`
  margin-bottom: 16px;
  border: none;
  outline: none;
  caret-color: ${({ theme }) => theme.color.palette.blue};
  background: ${({ theme, $isEdit }) =>
    $isEdit
      ? theme.color.neutral.surface.strong
      : theme.color.neutral.surface.bold};
  color: ${({ theme, $isEdit }) =>
    $isEdit ? theme.color.neutral.text.strong : theme.color.neutral.text.weak};
  ${({ theme }) => theme.font.display.medium[16]};
  resize: none;
  flex: 1;
`;

const Bottom = styled.footer`
  padding: 0 16px;
  height: 52px;
  position: relative;
  display: flex;
  align-items: center;
  border-top: ${({ theme }) => theme.objectStyles.border.dash};
  ${({ theme }) => theme.color.neutral.border.default};
`;

const TextCounter = styled.span`
  color: ${({ theme }) => theme.color.neutral.text.weak};
  ${({ theme }) => theme.font.display.medium[12]};
  position: absolute;
  top: -32px;
  right: 30px;
`;
