import { css } from '@emotion/react';
import { radius } from '../../styles/object';
import { color } from '../../styles/color';
import EditIcon from '../../assets/Icons/EditIcon';
import PlusIcon from '../../assets/Icons/PlusIcon';
import { font } from '../../styles/font';

type Props = {
  variant: 'issue' | 'comment' | 'edit' | 'label' | 'milestone' | 'success';
};

export default function Button({ variant }: Props) {
  const buttonValue =
    variant === 'issue'
      ? '이슈 작성'
      : variant === 'comment'
      ? '코멘트 작성'
      : variant === 'edit'
      ? '편집 완료'
      : variant === 'label'
      ? '레이블 추가'
      : variant === 'milestone'
      ? '마일스톤 추가'
      : '완료';

  return (
    <>
      <button css={commonButton}>
        {variant === 'edit' ? <EditIcon /> : <PlusIcon />} {buttonValue}
      </button>
    </>
  );
}

const commonButton = css`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
  width: 128px;
  height: 40px;
  padding: 0px 16px;
  border-radius: ${radius.medium};
  background-color: ${color.brand.surfaceDefault};
  color: ${color.brand.textDefault};
  font: ${font.availableMedium12};
`;
