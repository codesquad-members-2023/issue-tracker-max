import { useTheme } from '@emotion/react';
import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { ReactComponent as XSquare } from '@assets/icons/xSquare.svg';

import { Title } from '@components/addIssuePage/Title';
import { Body } from '@components/addIssuePage/Body';
import { InputContainer } from '@components/addIssuePage/InputContainer';
import { UserImage } from '@components/addIssuePage/UserImage';
import { UserImageContainer } from '@components/addIssuePage/UserImageContainer';
import { TextInput } from '@components/common/textInput/TextInput';
import { TextArea } from '@components/common/TextArea';
import { SideBar } from '@components/common/sideBar/SideBar';
import { ListSideBar } from '@components/common/sideBar/ListSideBar';
import { ButtonContainer } from '@components/addIssuePage/ButtonContainer';
import { Button } from '@components/common/Button';

type SelectedItems = {
  [key: number]: boolean;
};

const userImage = 'https://avatars.githubusercontent.com/u/57523197?v=4';
const availableFileSize = 1048576; //1MB

export const AddIssuePage: React.FC = ({}) => {
  const theme = useTheme() as any;
  const navigate = useNavigate();

  const [titleInput, setTitleInput] = useState<string>('');
  const [textAreaValue, setTextAreaValue] = useState<string>('');
  const [isDisplayingCount, setIsDisplayingCount] = useState(false);

  const [isFileTypeError, SetIsFileTypeError] = useState<boolean>(false);
  const [isFileSizeError, SetIsFileSizeError] = useState<boolean>(false);
  const [isFileUploading, setIsFileUploading] = useState<boolean>(false);
  const [isFileUploadFailed, setIsFileUploadFailed] = useState<boolean>(false);

  const uploadImage = async (file: File) => {
    try {
      setIsFileUploading(true);

      const formData = new FormData();
      formData.append('file', file);

      const response = await fetch('/your-server-endpoint', {
        method: 'POST',
        body: formData,
      });

      if (!response.ok) {
        throw new Error('File upload failed');
      }

      const data = await response.json();
      return data;
    } catch (error) {
      setIsFileUploadFailed(true);
    } finally {
      setIsFileUploading(false);
    }
  };

  const onFileChange = (e) => {
    if (e.target.files.length > 0) {
      const file = e.target.files[0];
      const fileName = file.name;

      if (file.size > availableFileSize) {
        SetIsFileSizeError(true);
        return;
      }

      if (!file.type.startsWith('image/')) {
        SetIsFileTypeError(true);
        return;
      }

      SetIsFileTypeError(false);
      SetIsFileSizeError(false);

      uploadImage(file); //파일 서버 업로드 로직

      setIsFileUploadFailed(false);
      setTextAreaValue((prevValue) => `${prevValue}![${fileName}](file path)`);
    }
  };

  // const [assigneesData, setAssigneesData] = useState<any[]>([]);
  // const [labelsData, setLabelsData] = useState<any[]>([]);
  // const [milestonesData, setMilestonesData] = useState<any[]>([]);

  const [selectedAssignees, setSelectedAssignees] = useState<SelectedItems>({});
  const [selectedLabels, setSelectedLabels] = useState<SelectedItems>({});
  const [selectedMilestones, setSelectedMilestones] = useState<SelectedItems>(
    {},
  );

  useEffect(() => {
    if (textAreaValue) {
      setIsDisplayingCount(true);
      const timer = setTimeout(() => setIsDisplayingCount(false), 2000);
      return () => clearTimeout(timer);
    }
  }, [textAreaValue]);

  const onMultipleSelectedAssignee = (index: number) => {
    setSelectedAssignees((prev) => ({ ...prev, [index]: !prev[index] }));
  };

  const onMultipleSelectedLabel = (index: number) => {
    setSelectedLabels((prev) => ({ ...prev, [index]: !prev[index] }));
  };

  const onSingleSelectedMilestone = (index: number) => {
    setSelectedMilestones({ [index]: true });
  };

  const onChange = (value: string) => {
    setTitleInput(value);
  };

  const onChangeTextArea = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setTextAreaValue(e.target.value);
  };

  return (
    <div
      css={{
        display: 'flex',
        flexDirection: 'column',
        gap: '24px',
      }}
    >
      <Title />
      <Body>
        <UserImageContainer>
          <UserImage image={userImage} />
        </UserImageContainer>
        <InputContainer>
          <TextInput
            value={titleInput}
            label="제목"
            inputType="text"
            placeholder="제목"
            onChange={onChange}
            height={56}
          />
          <TextArea
            letterCount={textAreaValue.length}
            textAreaValue={textAreaValue}
            isDisplayingCount={isDisplayingCount}
            isFileUploading={isFileUploading}
            isFileTypeError={isFileTypeError}
            isFileSizeError={isFileSizeError}
            isFileUploadFailed={isFileUploadFailed}
            onFileChange={onFileChange}
            onChangeTextArea={onChangeTextArea}
          />
        </InputContainer>
        <SideBar>
          <ListSideBar
            onSingleSelectedMilestone={onSingleSelectedMilestone}
            onMultipleSelectedAssignee={onMultipleSelectedAssignee}
            onMultipleSelectedLabel={onMultipleSelectedLabel}
            selectedAssignees={selectedAssignees}
            selectedLabels={selectedLabels}
            selectedMilestones={selectedMilestones}
          />
        </SideBar>
      </Body>
      <ButtonContainer>
        <Button
          typeVariant="ghost"
          size="M"
          onClick={() => {
            navigate('/');
          }}
        >
          <XSquare stroke={theme.neutral.text.default} />
          작성취소
        </Button>
        <Button typeVariant="contained" size="L" disabled={titleInput === ''}>
          완료
        </Button>
      </ButtonContainer>
    </div>
  );
};
