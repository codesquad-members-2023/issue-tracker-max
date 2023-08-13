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
import { ButtonContainer } from '@components/addIssuePage/ButtonContainer';
import { uploadFile } from 'apis/fileUpload';
import { ReactComponent as Plus } from '@assets/icons/plus.svg';
// todo issueDetailPage에 들어가면 줄바꿈해서 작성한게 그대로 보여야하는데
// 한줄로 보이는중
type DefaultFileStatusType = {
  typeError: boolean;
  sizeError: boolean;
  isUploading: boolean;
  uploadFailed: boolean;
};
type Props = {
  issueId: number;
  issueAuthor: User;
  typeVariant: 'issue' | 'default' | 'edit' | 'add';
  createdAt?: string;
  userId?: number;
  loginId?: string;
  isDisabled?: boolean;
  defaultValue: string;
  onAddComment?: (comment: any) => void;
  lastComment?: boolean; //임시 props
};

export const Comment: React.FC<Props> = ({
  issueId,
  issueAuthor,
  createdAt,
  userId,
  loginId,
  typeVariant = 'default',
  isDisabled = false,
  defaultValue,
  onAddComment,
}) => {
  const theme = useTheme() as any;

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
    // postNewComment(issueId, issueAuthor.userId, textAreaValue);

    try {
      // const responseData = await postNewComment(
      //   issueId,
      //   issueAuthor.userId,
      //   textAreaValue,
      // );
      // const newComment = responseData.data;
      // console.log(newComment);

      const newComment = {
        id: issueId,
        author: {
          userId: issueAuthor.userId,
          loginId: 'bono1234',
          image: '이미지 url',
        },
        contents: textAreaValue,
        createdAt: '2023-07-27T00:00:00',
      };
      if (onAddComment) {
        onAddComment(newComment);
        setTextAreaValue('');
      }
    } catch (error) {
      console.error('이슈 추가 에러:', error);
      // 에러처리
    }
  };

  // todo api에서 가져와서 바꾸기
  const onEditSubmit = async () => {
    const path = typeVariant === 'issue' ? `issues/contents` : `comments`;
    const method = typeVariant === 'issue' ? 'PATCH' : 'PUT';

    const body = {
      id: issueId,
      contents: textAreaValue,
    };

    try {
      const response = await fetch(
        `${import.meta.env.VITE_APP_BASE_URL}/${path}`,
        {
          method: method,
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(body),
        },
      );

      if (!response.ok) {
        throw new Error(`Error: ${response.statusText}`);
      }

      const responseData = await response.json();
      console.log('responseData', responseData);

      setTextAreaValue(body.contents);
      setPlaceholderValue(body.contents);
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

  const isAuthor = userId === issueAuthor?.userId;

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
                image="D"
                loginId={loginId}
                createdAt={createdAt}
                isAuthor={isAuthor}
              />
            }
            customStyle={wrapperStyle}
          >
            <div
              css={{
                paddingTop: '16px',
              }}
            >
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
            <ButtonContainer>
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
            </ButtonContainer>
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
