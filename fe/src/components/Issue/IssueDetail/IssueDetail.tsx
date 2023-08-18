import { Theme, css, useTheme } from '@emotion/react';
import SidebarDetail from './SidebarDetail';
import IssueDetailHeader from './IssueDetailHeader';
import Comment from './Comment';
import Button from '../../common/Button';
import { ReactComponent as PlusIcon } from '../../../assets/icon/plus.svg';
import IssueContent from '../IssueContent';
import { useEffect, useState } from 'react';
import { customFetch } from '../../../util/customFetch';
import { useNavigate, useParams } from 'react-router-dom';
import { OnlySuccessRes, PostCommentsRes } from '../../../type/Response.type';
import { IssueDetailResponse, IssueDetailType } from '../../../type/issue.type';

export default function IssueDetail() {
  const theme = useTheme();
  const { id } = useParams();
  const [content, setContent] = useState('');
  const [detailIssue, setDetailIssue] = useState<IssueDetailType>();
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      try {
        const issueData = await customFetch<IssueDetailResponse>({
          subUrl: `api/issues/${id}`,
        });

        if (issueData.success && issueData.data) {
          setDetailIssue(issueData.data);
        }
      } catch (error) {
        navigate('/sign-in');
      }
    })();
  }, []);

  const onStatusChange = (status: boolean) => {
    if (detailIssue) {
      setDetailIssue({
        ...detailIssue,
        isOpen: !status,
      });
    }
  };

  const onTitleChange = (editedTitle: string) => {
    if (detailIssue) {
      setDetailIssue({
        ...detailIssue,
        title: editedTitle,
      });
    }
  };

  const onChangeContent = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
  };

  const onDeleteIssue = async () => {
    const response = await customFetch<OnlySuccessRes>({
      subUrl: `api/issues/${id}`,
      method: 'DELETE',
    });

    if (response.success) {
      navigate('/');
    }
  };

  const onSubmitComment = async () => {
    try {
      const response = await customFetch<PostCommentsRes>({
        method: 'POST',
        subUrl: `api/issues/${id}/comments`,
        body: JSON.stringify({ content: content }),
      });

      if (response.success && response.data) {
        setDetailIssue({
          ...detailIssue,
          comments: [
            ...detailIssue!.comments,
            {
              id: response.data.id,
              writer: {
                id: response.data.writer.id,
                name: response.data.writer.name,
                imageUrl: response.data.writer.imageUrl,
              },
              content: response.data.content,
              createdAt: response.data.createdAt,
            },
          ],
        } as IssueDetailType);
        setContent('');
        return;
      }
    } catch (error) {
      //Memo: 에러 핸들링 필요
      throw error;
    }
  };

  const onDeleteComment = (commentId: number) => {
    setDetailIssue((prev) => {
      if (prev) {
        return {
          ...prev,
          comments: prev.comments.filter((comment) => comment.id !== commentId),
        };
      }
    });
  };

  const onAddImg = (imgMarkdown: string) => {
    setContent((prev) => `${prev}\n${imgMarkdown}\n`);
  };

  return (
    <div css={issueDetail(theme)}>
      {detailIssue && (
        <>
          <IssueDetailHeader
            issue={detailIssue}
            onTitleChange={onTitleChange}
            onStatusChange={onStatusChange}
            commentCount={detailIssue.comments.length}
          />
          <div className="divider" />
          <main className="main">
            <ul className="comments">
              {detailIssue.comments.map((comment) => (
                <Comment
                  key={comment.id}
                  issueId={detailIssue.id}
                  commentData={comment}
                  onDelete={onDeleteComment}
                />
              ))}
              <IssueContent
                value={content}
                onChange={onChangeContent}
                height="80px"
                withInfo
                onAddImg={onAddImg}
              />
              <Button
                icon={<PlusIcon />}
                color={theme.brand.textDefault}
                backgroundColor={theme.brand.surfaceDefault}
                value="코멘트 작성"
                onClick={onSubmitComment}
              />
            </ul>
            <SidebarDetail onClick={onDeleteIssue} />
          </main>
        </>
      )}
    </div>
  );
}

const issueDetail = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  gap: 24px;
  width: 1280px;
  margin: 32px auto 0;

  .divider {
    width: 100%;
    height: 1px;
    background-color: ${theme.neutral.borderDefault};
  }

  .main {
    display: flex;
    justify-content: space-between;

    .comments {
      display: flex;
      flex-direction: column;
      gap: 24px;
      width: 960px;

      > button {
        margin-left: auto;

        & svg path {
          stroke: ${theme.brand.textDefault};
        }
      }
    }
  }
`;
