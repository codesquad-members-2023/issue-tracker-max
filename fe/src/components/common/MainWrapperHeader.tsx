import { css } from '@emotion/react';
import { color } from '../../styles/color';
import { font } from '../../styles/font';
import { border, radius } from '../../styles/object';
import ChevronDownIcon from '../../assets/Icons/ChevronDownIcon';
import Taps from './Taps';
import Button from './Button';

type Props = {
  variant: 'issue' | 'label' | 'milestone';
};

export default function MainWrapperHeader({ variant }: Props) {
  return (
    <div css={mainWrapperHeader}>
      {variant === 'issue' ? (
        <>
          <div css={filterBar}>
            <button css={filterButton}>
              필터
              <ChevronDownIcon />
            </button>
            <input
              css={filterInput}
              type="text"
              value="is:issue is:open"
              onChange={() => {}}
            />
          </div>
          <div css={navWrapper}>
            <Taps />
            <Button variant={variant} />
          </div>
        </>
      ) : (
        <>
          <Taps />
          <Button variant={variant} />
        </>
      )}
    </div>
  );
}

const mainWrapperHeader = css`
  display: flex;
  justify-content: space-between;
  margin-bottom: 24px;
`;

const filterBar = css`
  display: flex;
  align-items: center;
  border-radius: ${radius.medium};
  border: ${border.default} ${color.neutral.borderDefault};
  border-radius: ${radius.medium};
`;

const filterButton = css`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 128px;
  height: 40px;
  padding: 0px 24px;
  border-radius: ${radius.medium} 0 0 ${radius.medium};
  background-color: ${color.neutral.surfaceDefault};
  font: ${font.availableMedium16};
  color: ${color.neutral.textDefault};
`;

const filterInput = css`
  position: relative;
  width: 440px;
  height: 40px;
  padding-left: 40px;
  border: none;
  border-left: ${border.default} ${color.neutral.borderDefault};
  border-radius: 0 ${radius.medium} ${radius.medium} 0;
  color: ${color.neutral.textWeak};
  background-color: ${color.neutral.surfaceBold};
  font: ${font.displayMedium16};
  background-image: url('/src/assets/Icons/search.svg');
  background-repeat: no-repeat;
  background-position: 15px center;
`;

const navWrapper = css`
  display: flex;
  justify-content: space-between;
  gap: 15px;
`;
