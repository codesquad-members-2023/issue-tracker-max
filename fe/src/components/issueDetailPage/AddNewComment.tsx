import { useTheme } from '@emotion/react';
import { Button } from '@components/common/Button';
import { Comment } from '@components/common/comment/Commentt';
import { ReactComponent as Plus } from '@assets/icons/plus.svg';

type Props = {
  disabled?: boolean;
};

export const AddNewComment: React.FC<Props> = ({ disabled = true }) => {
  const theme = useTheme() as any;
  return (
    <div
      css={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'flex-end',
        gap: '24px',
      }}
    >
      <Comment
        typeVariant="add"
        // letterCount={textAreaValue.length}
        // textAreaValue={contents}
        // onAddFileUrl={onAddFileUrl}
        // onChangeTextArea={onChangeTextArea}
        letterCount={1}
        // textAreaValue="1"
        onAddFileUrl={() => console.log('1')}
        onChangeTextArea={() => console.log('1')}
      />
      <Button typeVariant="contained" size="S" disabled={disabled}>
        <Plus stroke={theme.brand.text.default} />
        코멘트 작성
      </Button>
    </div>
  );
};
