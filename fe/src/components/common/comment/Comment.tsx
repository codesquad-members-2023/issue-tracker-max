import { useTheme } from '@emotion/react';
import { Box } from '../box/Box';
import { useState, useEffect } from 'react';
import { TextArea } from '../textArea/TextArea';
import { TextAreaInput } from '../textArea/TextAreaInput';
import { CommentHeader } from './CommentHeader';
import { Caption } from '../textArea/Caption';
import { AddButtons } from '../textArea/AddButtons';
import { Button } from '../Button';
import { ReactComponent as PaperClip } from '@assets/icons/paperclip.svg';
import { ReactComponent as XSquare } from '@assets/icons/xSquare.svg';
import { uploadFile } from 'apis/fileUpload';
import { ReactComponent as Plus } from '@assets/icons/plus.svg';
import { editComment, patchIssueContents, postNewComment } from 'apis/api';
import { getLocalStorageUserId } from 'apis/localStorage';
// todo issueDetailPage에 들어가면 줄바꿈해서 작성한게 그대로 보여야하는데
// 한줄로 보이는중
type DefaultFileStatusType = {
  typeError: boolean;
  sizeError: boolean;
  isUploading: boolean;
  uploadFailed: boolean;
};
type Props = {
  commentId?: number;
  issueId: number;
  issueAuthor: User;
  typeVariant: 'issue' | 'default' | 'edit' | 'add';
  createdAt?: string;
  // userId?: number;
  // loginId?: string;
  commentAuthor?: User;
  isDisabled?: boolean;
  defaultValue: string;
  onAddComment?: (comment: any) => void;
  lastComment?: boolean; //임시 props
};

export const Comment: React.FC<Props> = ({
  commentId,
  issueId,
  issueAuthor,
  createdAt,
  // loginId,
  commentAuthor,
  typeVariant = 'default',
  isDisabled = false,
  defaultValue,
  onAddComment,
}) => {
  const theme = useTheme() as any;

  //코멘트랑 textArea비슷한거 어케줄일지 생각하기..
  const [textAreaValue, setTextAreaValue] = useState<string>(defaultValue);
  const [placeholderValue, setPlaceholderValue] =
    useState<string>(defaultValue); //편집 취소시 돌아갈 값
  const [isEditing, setIsEditing] = useState(false);
  const [isDisplayingCount, setIsDisplayingCount] = useState(false);

  useEffect(() => {
    if (textAreaValue) {
      setIsDisplayingCount(true);
      const timer = setTimeout(() => setIsDisplayingCount(false), 2000);
      return () => clearTimeout(timer);
    }
  }, [textAreaValue]);

  const [fileStatus, setFileStatus] =
    useState<DefaultFileStatusType>(initialStatus);

  const initFileStatus = () => {
    setFileStatus(initialStatus);
  };

  const onFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    initFileStatus();

    if (e.target.files && e.target.files.length > 0) {
      const file = e.target.files[0];

      if (!file) {
        setFileStatus((prev) => ({ ...prev, uploadFailed: true }));
        return;
      }

      const fileName = file.name;

      if (file.size > AVAILABLE_FILE_SIZE) {
        setFileStatus((prev) => ({ ...prev, sizeError: true }));
        return;
      }

      if (!file.type.startsWith('image/')) {
        setFileStatus((prev) => ({ ...prev, typeError: true }));
        return;
      }

      try {
        setFileStatus((prev) => ({ ...prev, isUploading: true }));

        const fileUrl = await uploadFile(file);

        onAddFileUrl(fileName, fileUrl.fileUrl);
      } catch {
        setFileStatus((prev) => ({ ...prev, uploadFailed: true }));
      } finally {
        setFileStatus((prev) => ({ ...prev, isUploading: false }));
      }
    }
  };

  useEffect(() => {
    if (textAreaValue) {
      setIsDisplayingCount(true);
      const timer = setTimeout(() => setIsDisplayingCount(false), 2000);

      return () => clearTimeout(timer);
    }
  }, [textAreaValue]);

  const onChangeTextArea = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setTextAreaValue(e.target.value);
  };

  const onAddFileUrl = (fileName: string, fileUrl: string) => {
    setTextAreaValue((prevValue) => `${prevValue}![${fileName}](${fileUrl})`);
  };

  const onClickEdit = () => {
    setIsEditing(true);
  };

  const onAddSubmit = async () => {
    try {
      const userId = getLocalStorageUserId();
      const responseData = await postNewComment(issueId, userId, textAreaValue);
      const newComment = responseData.data;
      console.log(newComment);

      // const newComment = {
      //   id: issueId,
      //   author: {
      //     userId: issueAuthor.userId,
      //     loginId: 'bono1234',
      //     image: '이미지 url',
      //   },
      //   contents: textAreaValue,
      //   createdAt: '2023-07-27T00:00:00',
      // };
      if (onAddComment) {
        onAddComment(newComment);
        setTextAreaValue('');
      }
    } catch (error) {
      console.error('이슈 추가 에러:', error);
      // 에러처리
    }
  };

  const onEditSubmit = async () => {
    try {
      typeVariant === 'issue'
        ? await patchIssueContents(issueId, textAreaValue)
        : await editComment(commentId, textAreaValue);

      // 받아오는 응답: id만
      setIsEditing(false);
    } catch (error) {
      console.error('이슈 편집 에러:', error);
      // 에러처리
    }
  };

  const onEditCancel = () => {
    setIsEditing(false);
    setTextAreaValue(placeholderValue);
  };

  const wrapperStyle = {
    background: theme.neutral.surface.strong,
    '&:focus-within': {
      border: `${theme.border.default} ${theme.neutral.border.defaultActive}`,
    },
  };

  const isAuthor = getLocalStorageUserId() === issueAuthor?.userId;

  return (
    <>
      {typeVariant === 'add' && (
        <>
          <TextArea
            size="S"
            letterCount={textAreaValue.length}
            textAreaValue={textAreaValue}
            onAddFileUrl={onAddFileUrl}
            onChangeTextArea={onChangeTextArea}
            typeVariant="add"
          />

          <Button
            typeVariant="contained"
            size="S"
            disabled={false}
            onClick={onAddSubmit}
          >
            <Plus stroke={theme.brand.text.default} />
            코멘트 작성
          </Button>
        </>
      )}
      {(typeVariant === 'default' || typeVariant === 'issue') && (
        <>
          <Box
            header={
              <CommentHeader
                onClickEdit={onClickEdit}
                image={
                  typeVariant === 'issue'
                    ? issueAuthor.image
                    : commentAuthor?.image
                }
                loginId={
                  typeVariant === 'issue'
                    ? issueAuthor.loginId
                    : commentAuthor?.loginId
                }
                createdAt={createdAt} //이거 시간 표시로 바꾸기
                isAuthor={isAuthor}
              />
            }
            customStyle={wrapperStyle}
          >
            <div css={{ paddingTop: '16px' }}>
              <TextAreaInput
                typeVariant={isEditing ? 'add' : 'default'}
                textAreaValue={textAreaValue}
                placeholder={defaultValue}
                isDisabled={isDisabled}
                onChangeTextArea={onChangeTextArea}
                // fileUrl={'이걸 어떻게 주지??'}
              />
            </div>
            {isEditing && (
              <>
                <div
                  css={{
                    background: theme.neutral.surface.strong,
                    paddingTop: '16px',
                  }}
                >
                  <Caption
                    isDisplayingCount={isDisplayingCount}
                    letterCount={textAreaValue.length}
                  />
                  <AddButtons
                    onFileChange={onFileChange}
                    fileStatus={fileStatus}
                  >
                    <Button
                      typeVariant="ghost"
                      size="S"
                      css={{
                        pointerEvents: 'none',
                      }}
                    >
                      <PaperClip stroke={theme.neutral.text.weak} />
                      파일 첨부하기
                    </Button>
                  </AddButtons>
                </div>
              </>
            )}
          </Box>
          {isEditing && (
            <div
              css={{
                display: 'flex',
                justifyContent: 'flex-end',
                alignItems: 'center',
                gap: '16px',
              }}
            >
              <Button typeVariant="outline" size="S" onClick={onEditCancel}>
                <XSquare stroke={theme.brand.border.default} />
                편집 취소
              </Button>
              <Button
                typeVariant="contained"
                size="S"
                // disabled={titleInput === '' || isSubmiting}
                onClick={onEditSubmit}
              >
                편집 완료
              </Button>
            </div>
          )}
        </>
      )}
    </>
  );
};

const initialStatus = {
  typeError: false,
  sizeError: false,
  isUploading: false,
  uploadFailed: false,
};

const AVAILABLE_FILE_SIZE = 1048576; //1MB
