import { useTheme } from '@emotion/react';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { Title } from '@components/addIssuePage/Title';
import { Body } from '@components/addIssuePage/Body';
import { UserImage } from '@components/addIssuePage/UserImage';
import { UserImageContainer } from '@components/addIssuePage/UserImageContainer';
import { InputContainer } from '@components/addIssuePage/InputContainer';
import { TextArea } from '@components/common/textArea/TextArea';
import { SideBar } from '@components/common/sideBar/SideBar';
import { ListSideBar } from '@components/common/sideBar/ListSideBar';
import { ButtonContainer } from '@components/addIssuePage/ButtonContainer';
import { Button } from '@components/common/Button';
import { ReactComponent as XSquare } from '@assets/icons/xSquare.svg';
import { TextInput } from '@components/common/textInput/TextInput';
import { ISSUE_LIST_PAGE } from 'constants/PATH';
// import { ISSUE_DETAIL_PAGE } from 'constants/PATH';

type SelectionState = {
  assignees: number[];
  labels: number[];
  milestones: number | null;
};

// 추후 구현 보완시 추가
// type Props = {
//   authorId: number;
//   userImage: string;
// };

export const AddIssuePage: React.FC = ({}) => {
  const theme = useTheme() as any;
  const navigate = useNavigate();
  const userImage = 'https://avatars.githubusercontent.com/u/57523197?v=4'; //임시 이미지

  const [selections, setSelections] = useState<SelectionState>({
    assignees: [],
    labels: [],
    milestones: null, //todo 이름 단수형으로 변경
  });
  const [titleInput, setTitleInput] = useState<string>('');
  const [textAreaValue, setTextAreaValue] = useState<string>('');
  const [isSubmitError, setIsSubmitError] = useState<boolean>(false);
  const [isSubmiting, setIsSubmiting] = useState<boolean>(false);

  const onSubmit = async () => {
    const bodyData = {
      title: titleInput,
      contents: textAreaValue,
      authorId: 1,
      assigneeIds: selections.assignees,
      labelIds: selections.labels,
      milestoneId: selections.milestones,
    };

    try {
      setIsSubmiting(true);
      setIsSubmitError(false);
      const response = await fetch(
        `${import.meta.env.VITE_APP_BASE_URL}/issues/new`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(bodyData),
        },
      );

      if (!response.ok) {
        const errorData = await response.json();
        console.error('API Response Error:', errorData);
        setIsSubmitError(true);
        return;
      }

      const data = await response.json();

      navigate(`/issue/${data.id}`);

      return data;
    } catch (error) {
      console.error('API Call Error:', error);
      setIsSubmitError(true);
    } finally {
      setIsSubmiting(false);
    }
  };

  const onMultipleSelectedAssignee = (id: number) => {
    setSelections((prev) => ({
      ...prev,
      assignees: prev.assignees.includes(id)
        ? prev.assignees.filter((itemId) => itemId !== id)
        : [...prev.assignees, id],
    }));
  };

  const onMultipleSelectedLabel = (id: number) => {
    setSelections((prev) => ({
      ...prev,
      labels: prev.labels.includes(id)
        ? prev.labels.filter((itemId) => itemId !== id)
        : [...prev.labels, id],
    }));
  };

  const onSingleSelectedMilestone = (id: number) => {
    setSelections((prev) => ({
      ...prev,
      milestones: prev.milestones === id ? null : id,
    }));
  };

  const onChange = (value: string) => {
    setTitleInput(value);
  };

  const onChangeTextArea = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setTextAreaValue(e.target.value);
  };

  const onAddFileUrl = (fileName: string, fileUrl: string) => {
    setTextAreaValue((prevValue) => `${prevValue}![${fileName}](${fileUrl})`);
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
            typeVariant="add"
            onAddFileUrl={onAddFileUrl}
            onChangeTextArea={onChangeTextArea}
          />
        </InputContainer>
        <SideBar>
          <ListSideBar
            onSingleSelectedMilestone={onSingleSelectedMilestone}
            onMultipleSelectedAssignee={onMultipleSelectedAssignee}
            onMultipleSelectedLabel={onMultipleSelectedLabel}
            selections={selections}
          />
        </SideBar>
      </Body>
      <ButtonContainer>
        {isSubmitError && (
          <span
            css={{
              color: theme.danger.text.default,
              font: theme.fonts.displayMedium16,
            }}
          >
            이슈가 정상적으로 등록되지 않았습니다.
          </span>
        )}
        <Button
          typeVariant="ghost"
          size="M"
          onClick={() => {
            navigate(ISSUE_LIST_PAGE);
          }}
        >
          <XSquare stroke={theme.neutral.text.default} />
          작성취소
        </Button>
        <Button
          typeVariant="contained"
          size="L"
          disabled={titleInput === '' || isSubmiting}
          onClick={onSubmit}
        >
          완료
        </Button>
      </ButtonContainer>
    </div>
  );
};
