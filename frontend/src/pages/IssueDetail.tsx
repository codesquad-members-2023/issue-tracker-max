import { useContext, useEffect, useState } from 'react';
import { styled } from 'styled-components';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { AppContext } from '../main';
import { AxiosError } from 'axios';
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
import useAxiosPrivate from '../hooks/useAxiosPrivate';

const { AddButton, Assignee, Label, milestone } = ElementType;

type IssueDetail = {
  id: number;
  title: string;
  content: string;
  createdAt: string;
  user: {
    id: number;
    name: string;
    imgUrl: string;
  };
  close: boolean;
};

type IssueOptions = {
  assignees: Assignees[];
  labels: Labels[];
  milestone: Milestone;
};

type Assignees = {
  id: number;
  name: string;
  imgUrl: string;
};

type Labels = {
  id: number;
  name: string;
  backgroundColor: string;
  textColor: string;
};

type Milestone = {
  issueMileStoneDetailVo: {
    id: number;
    name: string;
  };
  completedRatio: number;
};

type CommentType = {
  id: number;
  createdAt: string;
  content: string;
  writer: {
    name: string;
    profileImg: string;
  };
};

export default function IssueDetail() {
  const { util } = useContext(AppContext);
  const navigate = useNavigate();
  const location = useLocation();
  const axiosPrivate = useAxiosPrivate();
  const [issueDetail, setIssueDetail] = useState<IssueDetail>({});
  const [issueOptions, setIssueOptions] = useState<IssueOptions>({});
  const [comments, setComments] = useState<CommentType[]>([]);
  const [newComment, setNewComment] = useState<string>('');

  const isFilled = !!newComment;

  useEffect(() => {
    const getIssueDetail = async () => {
      const endpoint = location.pathname;
      try {
        const issueDetails = await axiosPrivate.get('/api' + endpoint);
        const issueOptions = await axiosPrivate.get(
          '/api' + endpoint + '/options'
        );
        const issueComment = await axiosPrivate.get(
          '/api' + endpoint + '/comments'
        );
        setIssueDetail(issueDetails.data.message);
        setIssueOptions(issueOptions.data.message);
        setComments(issueComment.data.message);
      } catch (err) {
        console.error(err);
      }
    };
    getIssueDetail();
  }, []);

  const onNewCommentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const value = e.target.value;
    setNewComment(value);
  };

  const onAddFileMarkdown = (url: string) => {
    setNewComment((prev) => prev + `\n![image](${url})`);
  };

  const onSubmitNewComment = async () => {
    const endpoint = '/api' + location.pathname + '/comments';
    try {
      const res = await axiosPrivate.post(
        endpoint,
        { content: newComment },
        {
          headers: { 'Content-Type': 'application/json' },
        }
      );
      if (res.status === 200) {
        setNewComment('');
        const updatedComments = await axiosPrivate.get(
          '/api' + location.pathname + '/comments'
        );
        setComments(updatedComments.data.message);
      }
    } catch (err) {
      if (err instanceof AxiosError) {
        alert('에러가 발생했습니다.\n' + '실패 사유: ' + err.message);
        console.error(err);
      }
    }
  };

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
              <IssueTitle>{issueDetail.title}</IssueTitle>
              <IssueId>#{issueDetail.id}</IssueId>
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
              {issueDetail.close ? '닫힌 이슈' : '열린 이슈'}
            </InformationTag>
            {/* <span>
              이 이슈가 {issueDetail?.createdAt}에 {issueDetail?.user.name}님에
              의해 열렸습니다.
            </span> */}
          </StateInfo>
        </PostInfo>
        <Container>
          <CommentArea>
            <CommentElement
              key={issueDetail.id}
              userInfo={{
                userId: 1,
                userName: 'fuse123',
                // userId: issueDetail?.user.id,
                // userName: issueDetail?.user.name,
                // userImg: issueDetail?.user.imgUrl,
              }}
              timeStamp={issueDetail.createdAt}
              content={issueDetail.content}
            />
            {comments.map((comment) => {
              return (
                <CommentElement
                  key={comment.id}
                  userInfo={{
                    userName: comment.writer.name,
                    userImg: comment.writer.profileImg,
                  }}
                  content={comment.content}
                  timeStamp={comment.createdAt}
                />
              );
            })}
            <TextArea
              name="newComment"
              value={newComment}
              onChange={onNewCommentChange}
              placeholder="코멘트를 입력하세요"
              onAddFileMarkdown={onAddFileMarkdown}
            />
            <ButtonSmall
              type="submit"
              disabled={isFilled ? false : true}
              iconName="plus"
              onClick={onSubmitNewComment}>
              코멘트 작성
            </ButtonSmall>
          </CommentArea>
          <RightPanel>
            <SideBar
              sideBarItems={[
                [
                  { type: AddButton, text: '담당자' },
                  [
                    { type: Assignee, text: 'fuse123', checked: false },
                    { type: Assignee, text: 'geppetto', checked: false },
                  ],
                ],
                [
                  { type: AddButton, text: '레이블' },
                  [
                    {
                      type: Label,
                      labelProps: {
                        textColor: 'AAA333',
                        backgroundColor: 'FFF3FF',
                        name: 'feat',
                      },
                    },
                  ],
                ],
                [
                  { type: AddButton, text: '마일스톤' },
                  [
                    {
                      type: milestone,
                      text: '그룹프로젝트: 이슈트래커',
                      progress: 50,
                    },
                  ],
                ],
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
  gap: 16px;
`;
