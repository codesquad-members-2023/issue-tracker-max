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

type DefaultFileStatusType = {
  typeError: boolean;
  sizeError: boolean;
  isUploading: boolean;
  uploadFailed: boolean;
};

type Props = {
  typeVariant: 'default' | 'edit' | 'add';
  isDisabled?: boolean;
  letterCount?: number;
  placeholder?: string;
  onChangeTextArea: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  onAddFileUrl: (fileName: string, fileUrl: string) => void;
};

export const Comment: React.FC<Props> = ({
  typeVariant = 'default',
  isDisabled = false,
  letterCount,
  placeholder,
  onChangeTextArea,
  onAddFileUrl,
}) => {
  const theme = useTheme() as any;

  const [textAreaValue, setTextAreaValue] = useState<string>('');
  const [isDisplayingCount, setIsDisplayingCount] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
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

  const onClickEdit = () => {
    setIsEditing(true);
  };

  const isTyping = textAreaValue.length > 0;

  const wrapperStyle = {
    background: theme.neutral.surface.strong,
    '&:focus-within': {
      border: `${theme.border.default} ${theme.neutral.border.defaultActive}`,
    },
  };

  return (
    <>
      {typeVariant === 'add' && (
        <TextArea
          size="S"
          letterCount={textAreaValue.length}
          textAreaValue={textAreaValue}
          onAddFileUrl={onAddFileUrl}
          onChangeTextArea={onChangeTextArea}
        />
      )}
      {typeVariant === 'default' && (
        <>
          <Box
            header={
              <CommentHeader
                onClickEdit={onClickEdit}
                image="D"
                loginId="D"
                createdAt="12시간전"
                isAuthor={true}
              ></CommentHeader>
            }
            customStyle={wrapperStyle}
          >
            {isEditing ? (
              <>
                <div
                  css={{
                    background: theme.neutral.surface.strong,
                    paddingTop: '16px',
                  }}
                >
                  {isTyping && !isDisabled && (
                    <div
                      css={{
                        padding: '0 16px 8px 16px',
                        font: theme.fonts.displayMedium12,
                        color: theme.neutral.text.weak,
                      }}
                    >
                      코멘트를 입력하세요
                    </div>
                  )}
                  <TextAreaInput
                    textAreaValue={textAreaValue}
                    onChangeTextArea={onChangeTextArea}
                    isDisabled={isDisabled}
                    placeholder={placeholder}
                  ></TextAreaInput>
                  <Caption
                    isDisplayingCount={isDisplayingCount}
                    letterCount={letterCount}
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
            ) : (
              <div
                css={{
                  '&:focus-within': {
                    background: theme.neutral.surface.strong,
                  },
                  paddingTop: '16px',
                }}
              >
                {isTyping && !isDisabled && (
                  <div
                    css={{
                      padding: '0 16px 8px 16px',
                      font: theme.fonts.displayMedium12,
                      color: theme.neutral.text.weak,
                    }}
                  >
                    코멘트를 입력하세요
                  </div>
                )}
                <TextAreaInput
                  // textAreaValue={textAreaValue}
                  // onChangeTextArea={onChangeTextArea}
                  placeholder={textAreaValue}
                  isDisabled={true}
                ></TextAreaInput>
              </div>
            )}
          </Box>
          {isEditing && (
            <ButtonContainer>
              <Button
                typeVariant="ghost"
                size="M"
                onClick={() => {
                  setIsEditing(false);
                }}
              >
                <XSquare stroke={theme.neutral.text.default} />
                편집 취소
              </Button>
              <Button
                typeVariant="contained"
                size="L"
                // disabled={titleInput === '' || isSubmiting}
                // onClick={onSubmit}
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
